const URLParams=new URLSearchParams(window.location.search);
const pizzaId=URLParams.get('id');

axios.get(`http://localhost:8080/api/pizze/${pizzaId}`).then((res) => {
    console.log('richiesta ok', res); 
    document.querySelector('#name').innerHTML= res.data.nome;
    document.querySelector('#prezzo').innerHTML= res.data.prezzo;
   
    
}).catch((res) => {
    console.error('errore nella richiesta', res);
    alert('Errore durante la richiesta!');
});


function deletePizza() {
    const risposta = confirm('Sei sicuro?');

    if (risposta) {
        axios.delete(`http://localhost:8080/api/pizze/${pizzaId}`)
            .then((result) => {
                window.location.replace("http://localhost:8080");;
            }).catch((result) => {
                console.error('Errore nella richiesta', result);
                alert('Errore durante la richiesta!');
            });
    }