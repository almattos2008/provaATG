var form = document.getElementById('login-form');

ativo_element = document.querySelector('#ativo');
lado_element = document.querySelector('#lado');
quantidade_element = document.getElementById('quantidade');
preco_element = document.getElementById('preco');



form.addEventListener('submit', function(e) {
    e.preventDefault();
    ativo_val = ativo_element.value
    lado_val = lado_element.value
    quantidade_val = Number(quantidade_element.value)
    preco_val = Number(preco_element.value)

    getExposicao(ativo_val, lado_val, quantidade_val, preco_val)
    
    e.preventDefault();
});

async function getData() {
    const url = 'http://localhost:8080/healthCheck';
    try {
      const response = await fetch(url, 
       {method: "GET"});
      if (!response.ok) {
        console.log(response)
        throw new Error(`Response status: ${response.status}`);
      }
  
      const json = await response.json();
      console.log(json);
    } catch (error) {
      console.error(error.message);
    }
  }

async function getExposicao(ativo_val, lado_val, quantidade_val, preco_val) {
    const url = 'http://localhost:8080/order';
    try {
      const response = await fetch(url, 
        { headers: {
            "Content-Type": "application/json",
          },
        body: JSON.stringify({ ativo: ativo_val, lado: lado_val, quantidade: quantidade_val, preco: preco_val }),
        method: "POST",
        });
        const json = await response.json();
      if (!response.ok) {
        console.log(response.status)
        if(response.status == 400){
          console.log("Entrou")
          console.log(json)
          document.getElementById("sucesso").innerHTML = ""
          document.getElementById("exposicao").innerHTML = ""
          document.getElementById("erros").innerHTML = String(json.msg_erro)
        } else{
          throw new Error(`Response status: erro inesperado`);
        }
      }else{
        if (json.sucesso=='false'){
          document.getElementById("sucesso").innerHTML = ""
          document.getElementById("exposicao").innerHTML = ""
          document.getElementById("erros").innerHTML = String(json.msg_erro)
        }else{
          document.getElementById("sucesso").innerHTML = "Sucesso"
          document.getElementById("exposicao").innerHTML = 'Exposição financeira atual: R$ '+json.exposicao_atual.toString()
          document.getElementById("erros").innerHTML = ""
        }

       
        console.log(json);
      }
  
      
    } catch (error) {
      document.getElementById("erros").innerHTML = String(json.msg_erro)
    }
  }

