var a = 0;
var b = 0;
function enviar() {
	if(a == 1 && b == 1){
		console.log("aqui");
	    var json ={
	    		user_name: document.getElementById("nombre").value,
	    		user_last_name: document.getElementById("apellido").value,
	    		user_username: document.getElementById("usuario").value,
	    		user_password: document.getElementById("contrasena").value,
	    		user_email: document.getElementById("correo").value
	    }
	    let configs = {
	    		method: 'post',
	    		body: JSON.stringify(json),
	    		withCredentials: true,
	    		credentials: 'include',
	    		headers: {
	    			'Content-type': 'application/json'
	    		}
	    }
	    fetch('../Registro', configs)
	    	.then(res => res.json())
	    	.then(data => {console.log(data)
	    			if (data.status ==  200){
	        	    	//location.href ="../login/index.html";
	        	    }
	    	a = data.user_name;
	    	});
	}
	    
   }
function checkEmail() {
    var email = document.getElementById('correo');
    var mailFormat = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;

    if (!mailFormat.test(email.value)) {
        alert('Direccion de correo invalida');
        email.focus;
        return false;
    }else{
    	a = 1;
    }
}

function checkPassword(){
    var password = document.getElementById('contrasena');
    var passwordFormat= /(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{6,}/;

    if (!passwordFormat.test(password.value)) {
        alert('La contrasena debe tener al menos un numero, una letra mayuscula y una letra minuscula con minimo 6');
        password.focus;
        return false;
        console.log(aa);
    }else{
    	b = 1;
    }
}