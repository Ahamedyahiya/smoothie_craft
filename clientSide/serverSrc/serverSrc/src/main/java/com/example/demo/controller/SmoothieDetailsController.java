package com.example.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.model.SmoothieDetails;
import com.example.demo.repository.SmoothieDetailsRepository;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/smoothies")
public class SmoothieDetailsController {

	  @Autowired
	    private SmoothieDetailsRepository repository;
	  private static final Logger logger = Logger.getLogger(SmoothieDetailsController.class.getName());

	  @GetMapping("/getAllSmoothie/{calories}")
	  public ResponseEntity<List<SmoothieDetails>> getAllSmoothies(@PathVariable Long calories) {
	      List<SmoothieDetails> smoothies = repository.findSmoothieByCalories(calories);

	      if (smoothies.isEmpty()) {
	          return ResponseEntity.noContent().build(); 
	      }

	      return ResponseEntity.ok(smoothies); 
	  }


    @GetMapping("/getSmoothie/{id}")
    public SmoothieDetails getSmoothieById(@PathVariable Long id) {
    	 return repository.findById(id).orElseThrow(() -> new RuntimeException("Smoothie not found!"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<SmoothieDetails> updateSmoothie(
            @PathVariable Long id,
            @RequestParam("image") MultipartFile file,
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("ingredients") String ingredients,
            @RequestParam("directions") String direction,
            @RequestParam("nutrition") String nutrition) {

        SmoothieDetails existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Smoothie not found with ID: " + id));

        try {
            if (file != null && !file.isEmpty()) {
    
                if (existing.getSmoothieImage() != null) {
                    File oldFile = new File(existing.getSmoothieImage());
                    if (oldFile.exists()) {
                        oldFile.delete();
                    }
                }

                String uploadDir =  System.getProperty("user.dir") +"uploads/smoothies/";
                File dir = new File(uploadDir);
                if (!dir.exists()) {
                    dir.mkdirs();
                }

                String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
                String filePath = uploadDir + fileName;
                try {
                    File targetFile = new File(filePath);
                    FileCopyUtils.copy(file.getInputStream(), new FileOutputStream(targetFile));
                } catch (IOException e) {
                    logger.info("File transfer failed: " + e.getMessage()+"{0}"+ e);
                    throw new RuntimeException("Failed to save file: " + e.getMessage());
                }
                existing.setSmoothieImage(filePath);
            }

            // Update other fields
            existing.setTitle(title);
            existing.setDescription(description);
            existing.setIngredients(ingredients);
            existing.setDirection(direction);
            existing.setNutrition(nutrition);

            SmoothieDetails updatedSmoothie = repository.save(existing);
            return ResponseEntity.ok(updatedSmoothie);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null); // Optionally include error details
        }
    }


    @DeleteMapping("/deletesmoothie/{id}")
    public ResponseEntity<String> deleteSmoothie(@PathVariable Long id) {
        SmoothieDetails existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Smoothie not found with ID: " + id));

        try {

            if (existing.getSmoothieImage() != null) {
                File imageFile = new File(existing.getSmoothieImage());
                if (imageFile.exists() && imageFile.delete()) {
                    System.out.println("Image file deleted successfully: " + imageFile.getPath());
                } else {
                    System.out.println("Failed to delete image file: " + imageFile.getPath());
                }
            }

          
            repository.deleteById(id);

            return ResponseEntity.ok("Smoothie deleted successfully");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error deleting smoothie: " + e.getMessage());
        }
    }

    @PostMapping("/save")
    public ResponseEntity<SmoothieDetails> saveSmoothie(
        @RequestParam("image") MultipartFile file,
        @RequestParam("smoothieId") String smoothieId,
        @RequestParam("title") String title,
        @RequestParam("description") String description,
        @RequestParam("ingredients") String ingredients,
        @RequestParam("directions") String directions,
        @RequestParam("nutrition") String nutrition,
        @RequestParam("calories") Long calories
        
    ) {
        try {
           
            if (!file.getContentType().startsWith("image/")) {
                throw new RuntimeException("Invalid file type. Only image files are allowed.");
            }
            
            if (file.isEmpty()) {
                throw new RuntimeException("No file uploaded.");
            }
            logger.info("File name: " + file.getOriginalFilename());
           
            String uploadDir =  System.getProperty("user.dir") +"uploads/smoothies/";
            File dir = new File(uploadDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            logger.info("File is: " + dir);
            
            String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            String filePath = uploadDir + fileName;
            logger.info("fileName is: " + fileName);
            logger.info("filePath is: " + filePath);
            //file.transferTo(new File(filePath));
            try {
                File targetFile = new File(filePath);
                FileCopyUtils.copy(file.getInputStream(), new FileOutputStream(targetFile));
            } catch (IOException e) {
                logger.info("File transfer failed: " + e.getMessage()+"{0}"+ e);
                throw new RuntimeException("Failed to save file: " + e.getMessage());
            }

            logger.info("file is: " + file);
            SmoothieDetails smoothie = new SmoothieDetails();
           
            smoothie.setSmoothieImage(filePath); 
            smoothie.setTitle(title);
            smoothie.setDescription(description);
            smoothie.setIngredients(ingredients);
            smoothie.setDirection(directions);
            smoothie.setNutrition(nutrition);
            smoothie.setCalories(calories);
            logger.info("Smoothie details: " + smoothie);
            SmoothieDetails savedSmoothie = repository.save(smoothie);

            return ResponseEntity.ok(savedSmoothie);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body(null); 
        }
    }
    @PostMapping("/bulk-import")
    public ResponseEntity<?> bulkImportSmoothies(@RequestBody List<SmoothieDetails> smoothieList) {
        try {
            List<SmoothieDetails> smoothiesToSave = smoothieList.stream().map(dto -> {
                SmoothieDetails smoothie = new SmoothieDetails();
                smoothie.setTitle(dto.getTitle());
                smoothie.setDescription(dto.getDescription());
                smoothie.setIngredients(dto.getIngredients());
                smoothie.setDirection(dto.getDirection());
                smoothie.setNutrition(dto.getNutrition());
                smoothie.setCalories(dto.getCalories()); 				
                smoothie.setSmoothieImage(null); // or some default image path
                return smoothie;
            }).toList();

            List<SmoothieDetails> savedSmoothies = repository.saveAll(smoothiesToSave);
            return ResponseEntity.ok(savedSmoothies);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Bulk import failed: " + e.getMessage());
        }
    }

    @GetMapping("/getAllSmoothies")
    public ResponseEntity<List<SmoothieDetails>> getAllSmoothies() {
        List<SmoothieDetails> smoothies = repository.findAll();

        if (smoothies.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(smoothies);
    }
}
