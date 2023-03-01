console.log('JS OK!');

pizzasList();

function pizzasList() {
    axios.get('http://localhost:8080/api/pizze')
        .then((result) => {
            const pizzasList = result.data;
            document.querySelector('#pizzas_table').innerHTML = '';
            pizzasList.forEach(pizza => {
                document.querySelector('#pizzas_table').innerHTML += `
                <tr>
                    <td><a href="#"> ${pizza.id} </a></td>
                    <td>${pizza.nome}</td>
                    <td>${pizza.prezzo}</td>
                </tr>`;
            });

        }).catch((result) => {
            console.error('Errore nella richiesta', result);
            alert('Errore durante la richiesta');
        })
}