/**
 * Execute an AJAX request using XMLHttpRequest (XHR) and load the response into a target.
 *
 * @param {string} targetSelector - The CSS selector of the HTML element to load the response into.
 * @param {string} url - The URL to send the request to.
 * @param {Object} [params={}] - Optional parameters to send with the request.
 */
function xhrLoad(targetSelector, url, params = {}) {
    // Créer un nouvel objet XMLHttpRequest
    const xhr = new XMLHttpRequest();

    // Transformer les paramètres en chaîne de requête
    const queryString = new URLSearchParams(params).toString();

    // Ajouter la chaîne de requête à l'URL si des paramètres ont été fournis
    if (queryString) {
        url += '?' + queryString;
    }

    // Initialiser une nouvelle requête en utilisant la méthode GET
    xhr.open('GET', url, true);
	xhr.setRequestHeader("X-Requested-With", "XMLHttpRequest");

    // Définir ce qui doit se produire lorsque la requête a terminé le chargement
    xhr.onreadystatechange = function() {
        // readyState 4 indique que la requête a terminé le chargement et que la réponse est prête
        // status 200 indique une réponse HTTP réussie
        if (xhr.readyState == 4 && xhr.status == 200) {
            // Extraire le texte de la réponse
            const responseText = xhr.responseText;

            // Imprimer le texte de la réponse à la console
            console.log(responseText);

            // Sélectionner l'élément HTML cible
            const targetElement = document.querySelector(targetSelector);
            
            // Vérifier si l'élément cible existe
            if (targetElement) {
                // Charger le texte de la réponse dans l'élément cible
                targetElement.innerHTML = responseText;
            } else {
                console.error(`No element matches the selector "${targetSelector}"`);
            }
        }
    };
    // Envoyer la requête
    xhr.send();
}

/**
 * Execute an AJAX request using the Fetch API and load the response into a target.
 *
 * @param {string} targetSelector - The CSS selector of the HTML element to load the response into.
 * @param {string} url - The URL to send the request to.
 * @param {Object} [params={}] - Optional parameters to send with the request.
 */
async function load(targetSelector, url, params = {}) {
    // Transformer les paramètres en chaîne de requête
    const queryString = new URLSearchParams(params).toString();
    // Ajouter la chaîne de requête à l'URL si des paramètres ont été fournis
    if (queryString) {
        url += '?' + queryString;
    }
    // Exécuter la requête en utilisant Fetch
    try {
        const response = await fetch(url, {
			headers: { "X-Requested-With": "XMLHttpRequest" } 
		});

        // Vérifier si la requête a réussi
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }

        // Extraire le texte de la réponse
        const responseText = await response.text();

        // Imprimer le texte de la réponse à la console
        console.log(responseText);

        // Sélectionner l'élément HTML cible
        const targetElement = document.querySelector(targetSelector);

        // Vérifier si l'élément cible existe
        if (targetElement) {
            // Charger le texte de la réponse dans l'élément cible
            targetElement.innerHTML = responseText;
        } else {
            console.error(`No element matches the selector "${targetSelector}"`);
        }
    } catch (error) {
        console.error('There was a problem with the fetch operation: ', error);
    }
}
