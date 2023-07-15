/**
 * Efface tous les champs d'entrée d'un formulaire et le soumet.
 *
 * @param {string} id - L'identifiant du formulaire à effacer et à soumettre.
 * 
 * @example
 * // Pour effacer et soumettre le formulaire avec l'ID 'myForm'.
 * clearForm('myForm');
 */
function clearForm(id) {
    // Récupération du formulaire par son ID
    const form = document.getElementById(id);
    
    // Récupération de tous les éléments d'entrée dans le formulaire
    const inputs = form.getElementsByTagName('input');
    
    // Parcours de tous les inputs du formulaire
    Array.from(inputs).forEach(input => {
        // vider le champ
        input.value = '';
    });

    // Chercher le submit dans le formulaire
    const submitButton = form.querySelector('[type="submit"]');
    if (submitButton) {
		console.log('simuler le clique sur submit');
		// Simuler un clic sur le bouton submit
    	submitButton.click();
	} else {
	    // Soumission du formulaire pour executer la recherche
		form.submit();
	}

}
