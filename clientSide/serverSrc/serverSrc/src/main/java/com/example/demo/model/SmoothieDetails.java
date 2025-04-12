package com.example.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "smoothie_details")
public class SmoothieDetails {

	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "smoothie_id", nullable = false)
	    private Long smoothieId;

	    @Column(name = "smoothie_image")
	    private String smoothieImage;

	    @Column(name = "title", nullable = false)
	    private String title;

	    @Column(name = "description")
	    private String description;

	    @Column(name = "ingredients")
	    private String ingredients;

	    @Column(name = "direction")
	    private String direction;
	    
	    @Column(name = "nutrition")
	    private String nutrition ;
	
	    @Column(name = "calories")
	    private Long calories ;
	    
	    public Long getSmoothieId() {
	        return smoothieId;
	    }

	    public void setSmoothieId(Long smoothieId) {
	        this.smoothieId = smoothieId;
	    }

	    public String getSmoothieImage() {
	        return smoothieImage;
	    }

	    public void setSmoothieImage(String smoothieImage) {
	        this.smoothieImage = smoothieImage;
	    }

	    public String getTitle() {
	        return title;
	    }

	    public void setTitle(String title) {
	        this.title = title;
	    }

	    public String getDescription() {
	        return description;
	    }

	    public void setDescription(String description) {
	        this.description = description;
	    }

	    public String getIngredients() {
	        return ingredients;
	    }

	    public void setIngredients(String ingredients) {
	        this.ingredients = ingredients;
	    }

	    public String getDirection() {
	        return direction;
	    }

	    public void setDirection(String direction) {
	        this.direction = direction;
	    }

	    public String getNutrition() {
	        return nutrition;
	    }

	    public void setNutrition(String nutrition) {
	        this.nutrition = nutrition;
	    }
	
	    public Long getCalories() {
	        return calories;
	    }

	    public void setCalories(Long calories) {
	        this.calories = calories;
	    }
	    
	}
