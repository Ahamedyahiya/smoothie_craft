document.getElementById('weightForm').addEventListener('submit', function(event) {
    event.preventDefault();

    const weight = parseFloat(document.getElementById('weight').value);

    if (weight && weight > 0 ) {
    
        const protein = weight * 1;     
        const carbs = weight * 5;    
        const fat = weight * 1;      
      
    
        const calories = (protein * 1) + (carbs * 1) + (fat * 3.5);     
        const kcal = (protein * 4) + (carbs * 4) + (fat * 3.5);          

        
        document.getElementById('nutritionInfo').innerHTML = `
          
            <p>your maintenance protein is: ${protein}g</p>
            <p>your maintenance carbs is: ${carbs}g</p>
            <p>your maintenance Fat is: ${fat}g</p>
            <p>your smoothie calories is: ${calories} </p>
            <p>your  maintenance kcal: ${kcal} </p>

        `;
    } else {
        document.getElementById('nutritionInfo').textContent = 'Please enter a valid weight.';
    }
   
});
document.getElementById('lossform').addEventListener('submit', function(event) {
    event.preventDefault();

    const gain = parseFloat(document.getElementById('loss').value);

    switch(gain){
        case 2:
            document.getElementById('lossinfo').innerHTML = 
         
           ` <p>Aim for a caloric surplus of about 500 extra calories per day</p>
             <p>Eat your maintenance calories -500 kcal extra</p>
             <p>Aim to achieve 25 percentage of calories goal with smoothie and remaining with foods</p> `
        break;
        case 4:
            document.getElementById('lossinfo').innerHTML = 
         
           ` <p>Aim for a caloric surplus of about 500 extra calories per day</p>
             <p>Eat your maintenance calories -1000 kcal extra</p>
             <p>Aim to achieve 25 percentage of calories goal with smoothie and remaining with foods</p>  ` 
         break;
         
       default:
        document.getElementById('lossinfo').textContent = 'an month weight gain can 2kg or 4 kg,More gain cannot';
    }  
   
});