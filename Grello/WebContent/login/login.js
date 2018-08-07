var userLogin, boardId, columnId, cardId, titulo, ID, idInvitado, tipoInvitado;

function enviar(){
    var json ={
            user_username: document.getElementById("usuario").value,
            user_password: document.getElementById("contrasena").value
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
    fetch('../Login', configs)
        .then(res => res.json())
        .then(data => {console.log(data)
        	let userData = data.userData;
            if(data.status == 200){
            	document.location.replace(`http://localhost:8080/Grello/home/Home.html`)
                localStorage.setItem('sesion', JSON.stringify(userData));
                localStorage.setItem('id', JSON.stringify(userData.user_id))
            }
        });
}


//------------------------------------------------crear----------------------------------------------------------

function crearTablero(){
	console.log( "userData" + localStorage.getItem("sesion"));
	console.log(localStorage.getItem("id"));
	userLogin = localStorage.getItem("id");
	
	var json ={
            tipo: "crear",
            board_name: document.getElementById("cardName").value,
            user_id: userLogin
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
    fetch('../Tableros', configs)
        .then(res => res.json())
        .then(data => {console.log(data)
        	let boardData = data.userBoard;
            if(data.status == 200){
            	let boardData = data.boardData;
                //location.href ="../tableros/tablero.html";
                localStorage.setItem('tableros', JSON.stringify(boardDData));
                localStorage.setItem('nombreTablero', JSON.stringify(boardData.board_name));
                localStorage.setItem('boardId', JSON.stringify(boardData.board_id))
               
            }
        });	
	 window.location.reload(false);
	
}


//constructor(localStorage.getItem("nombreTablero"));

function crearColumna(){
	userLogin = localStorage.getItem("id")
	
	var json ={
            board_id: Id,
            column_name: document.getElementById("column_name").value,
            user_id: userLogin
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
    fetch('../Columna', configs)
        .then(res => res.json())
        .then(data => {console.log(data)
        	let columnData = data.columnData;
            if(data.status == 200){
            	console.log("Se agrego la columna ")
            	window.location.reload(false);
                localStorage.setItem('columnas', JSON.stringify(columnData));
                localStorage.setItem('columnId', JSON.stringify(columnData.column_id));
                
            }
        });
	
	
}

function crearTarjeta(t){
	
	userLogin = localStorage.getItem("id");
	
	var json ={
            column_id: t,
            user_id: userLogin,
            card_name: document.getElementById("cardName").value,
            card_description: document.getElementById("cardDescription").value
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
    fetch('../Tarjetas', configs)
        .then(res => res.json())
        .then(data => {console.log(data)
        	let cardData = data.userBoard;
            if(data.status == 200){
                localStorage.setItem('tarjetas', JSON.stringify(cardData));
                localStorage.setItem('cardId', JSON.stringify(cardData.card_id))
            }
        });
	window.location.reload(false);
	
	
}
function crearTarjeta(t){
	
	userLogin = localStorage.getItem("id");
	
	var json ={
            column_id: t,
            user_id: userLogin,
            card_name: document.getElementById("cardName").value,
            card_description: document.getElementById("cardDescription").value
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
    fetch('../Tarjetas', configs)
        .then(res => res.json())
        .then(data => {console.log(data)
        	let cardData = data.userBoard;
            if(data.status == 200){
                localStorage.setItem('tarjetas', JSON.stringify(cardData));
                localStorage.setItem('cardId', JSON.stringify(cardData.card_id))
            }
        });
	window.location.reload(false);
	
	
}

function crearComentario(t){
userLogin = localStorage.getItem("id");
	
	var json ={
            card_id: t,
            user_id: userLogin,
            comment_text: document.getElementById("comment_text").value
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
    fetch('../Comentarios', configs)
        .then(res => res.json())
        .then(data => {console.log(data)
        	let cardData = data.userBoard;
            if(data.status == 200){
            	window.location.reload(false);
            }
        });
	
}

//------------------------------Transaladar direccion--------------------------------------------------
const goToDetails = (id) => {
	document.location.replace(`http://localhost:8080/Grello/tableros/tableros.html?id=${id}`)
}

const goToUpdate = (id) => {
	ID = id;
	console.log("Estamos en goToUpdate")
	console.log("Este es: "+ ID);
	console.log(document.location);
}

const goToUpdateInvited = (id, tipo) => {
	idInvitado = id;
	console.log("ID del invitado: "+ idInvitado);
	tipoInvitado = tipo;
	console.log("Tipo de invitado: " + tipoInvitado);
	modal()
}

const goToBuscador = (id) =>{
	document.location.replace(`http://localhost:8080/Grello/buscador/index.html?id=${id}`)
}

//------------------------------------------------Leer---------------------------------------------------------------
function leerTablero(){
		userLogin = localStorage.getItem("id");
		/*var json ={
	            tipo: "leer",
	            user_id: userLogin
	    }*/
	    
		var url = '../Tableros?user_id='+userLogin;
	    let configs = {
	            method: 'get',
	            withCredentials: true,
	            credentials: 'include',
	            headers: {
	                'Content-type': 'application/json'
	            }
	    }
	    fetch(url, configs)
	        .then(res => res.json())
	        .then(data => {
	        	console.log(data)
	        	let boardData = data.response;
	        	
        		if(data.status == 200){
        			for (var i = 0; i < boardData.length; i++){
	        			console.log("board id "+i+": "+boardData[i].board_id);	 
	        			let container = `
		        			<div class="col-md-4" style="padding-bottom:10px">
		        				<button id="modificar" type="button" onclick="goToUpdate(${boardData[i].board_id})" class="btn btn-info add-new" style="background-color: #1b9891;" data-toggle="modal" data-target="#update"><img src="../img/plus.png" style="width:30px; height:30px;" >
									Modificar
								</button>
								<button id="eliminar" type="button" onclick="borrarTablero(${boardData[i].board_id})" class="btn btn-info add-new" style="background-color: #1b9891;" data-toggle="modal" ><img src="../img/delete.png" style="width:30px; height:30px;">
								</button>
		        				<div onclick="goToDetails(${boardData[i].board_id})" onclass="card w-40" style="width: inherit; margin-top:auto;">
			        				<div class="card-header text-center">  
			        					
										<br>
										${boardData[i].board_name}
		        						<div class="card-body"></div>
										<br>
		        					</div>
		        				</div>	
		        			</div>`;
	    	        	document.getElementById("boards").innerHTML += container; 
	    	        	
		                /*localStorage.setItem('tableros', JSON.stringify(boardData));
		                localStorage.setItem('boardId', JSON.stringify(boardData));
		                localStorage.setItem('boardName', JSON.stringify(boardData.board_name))*/
        			}	
        		}	
	        	 
	        });
	
	
}

function leerTipoTablero(t){
	var json ={
            tipo: "leerTipo",
            board_id: t
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
    fetch('../Tableros', configs)
        .then(res => res.json())
        .then(data => {
        	console.log(data)
        	let userBoard = data.response;
        	
    		if(data.status == 200){
    			console.log("Se realizo todo");
    			console.log("userBoard: "+userBoard.type_board_user_id);
    			localStorage.setItem('tipoTablero', JSON.stringify(userBoard.type_board_id))
    		}	
        	 
        });


}

/*function leerTableroOtroUsuario(i, t){
	var json ={
            tipo: "leer",
            user_id: i,
            type_board_user_id:t
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
    fetch('../Tableros', configs)
        .then(res => res.json())
        .then(data => {
        	console.log(data)
        	let boardData = data.response;
        	
    		if(data.status == 200){
    			for (var i = 0; i < boardData.length; i++){
        			console.log("board id "+i+": "+boardData[i].board_id);	        			
        			let container = `
	        			<div class="col-md-4" style="padding-bottom:10px">
	        				<button id="modificar" type="button" onclick="goToUpdate(${boardData[i].board_id})" class="btn btn-info add-new" style="background-color: #1b9891;" data-toggle="modal" data-target="#update"><img src="../img/plus.png" style="width:30px; height:30px;" >
								Modificar
							</button>
							<button id="eliminar" type="button" onclick="borrarTablero(${boardData[i].board_id})" class="btn btn-info add-new" style="background-color: #1b9891;" data-toggle="modal" ><img src="../img/delete.png" style="width:30px; height:30px;">
							</button>
	        				<div onclick="goToDetails(${boardData[i].board_id})" onclass="card w-40" style="width: inherit; margin-top:auto;">
		        				<div class="card-header text-center">  
		        					
									<br>
									${boardData[i].board_name}
	        						<div class="card-body"></div>
									<br>
	        					</div>
	        				</div>	
	        			</div>`;
    	        	document.getElementById("boards").innerHTML += container; 
    	        	
	                //localStorage.setItem('tableros', JSON.stringify(boardData));
	                //localStorage.setItem('boardId', JSON.stringify(boardData));
	                //localStorage.setItem('boardName', JSON.stringify(boardData.board_name))
    			}	
    		}	
        	 
        });
}*/



function leerColumna(id){
    
	var url = '../Columna?board_id='+id;
    let configs = {
            method: 'get',
            //body: JSON.stringify(json),
            withCredentials: true,
            credentials: 'include',
            headers: {
                'Content-type': 'application/json'
            }
    }
    fetch(url, configs)
        .then(res => res.json())
        .then(data => {
        	console.log(data)
        	let arrayColumn = data.response;
        	
    		if(data.status == 200){
    			for (var i = 0; i < arrayColumn.length; i++){
	    			console.log("column_id "+i+": "+arrayColumn[i].column_id);
	    			leerTarjeta(arrayColumn[i].column_id, i);
	    			var column=`
	    				<div class="column" ondrop="drop(event)" ondragover="allowDrop(event)">
							<div class="column-header" ><input id="i-header" type="text" class="form-control" value="${arrayColumn[i].column_name}"/>
							
								<a class="delete-column" title="Borrar columna" data-toggle="tooltip" onclick="borrarColumna(${arrayColumn[i].column_id})">
									<img src="../img/delete.png" style="width:25px; hight:25px;">
								</a>
								<button id="modificar" type="button" onclick="goToUpdate(${arrayColumn[i].column_id})" class="btn add-column"  data-toggle="modal" data-target="#update">
									<img src="../img/update.png" style="width:25px; hight:25px;">
								</button>
								
	    					</div>
		    				
		    				
		    				<button type="button" id="cardButton${i}" onclick="goToUpdate(${arrayColumn[i].column_id})" class="btn add-card"  data-toggle="modal" data-target="#insertCard">
		    					<img src="../img/agregar.png" style="width:25px; hight:25px;">
		    				</button>
	    				</div>
		    			`
	    				$(column).insertBefore($("#card-crear").parent());
	    				
    			}	
    		}
        });	

}



function leerTarjeta(t, x){
	
	var url = '../Tarjetas?column_id='+t;
    let configs = {
            method: 'get',
            withCredentials: true,
            credentials: 'include',
            headers: {
                'Content-type': 'application/json'
            }
    }
    fetch(url, configs)
        .then(res => res.json())
        .then(data => {
        	console.log(data)
        	let arrayCard = data.response;
        	
    		if(data.status == 200){
    			for (var i = 0; i < arrayCard.length; i++){
        			console.log("board id "+i+": "+arrayCard[i].card_id);	
        			var card = `
        				<div class="card" draggable="true" ondragstart="drag(event)">
        				<div class="card-header"> <input type="text" id="c-header" class="form-control" value="${arrayCard[i].card_name}" placeholder="Titulo"/></div>
        					<div class="card-body">
        						<textarea id="card-a" class="form-control" type="text" name="card" placeholder="Escriba algo aqui" >${arrayCard[i].card_description}</textarea>
        						<a class="delete-card" title="Delete Card" data-toggle="tooltip" onclick="borrarTarjeta(${arrayCard[i].card_id})">
        							<img src="../img/delete.png" style="width:25px; hight:25px;"/>
    							</a>
        						<button type="button" onclick="goToUpdate(${arrayCard[i].card_id})" data-toggle="modal" data-target="#updateCard" class="btn btn-c">
        							<img src="../img/update.png" style="width:25px; hight:25px;">
        						</button>
        						<button id="archivo" type="button"  onclick="leerArchivo(${arrayCard[i].card_id}), goToUpdate(${arrayCard[i].card_id})" class="btn add-column" data-toggle="modal" data-target="#archivos">
									<img src="../img/archivo.png" style="width:25px; hight:25px;">
								</button>
								<button id="co" type="button"  onclick="leerComentario(${arrayCard[i].card_id}), goToUpdate(${arrayCard[i].card_id})"class="btn add-column" data-toggle="modal" data-target="#comentario">
									<img src="../img/comentario.png" style="width:25px; hight:25px;">
								</button>
        					</div>
        				</div>
        			`
        				$(card).insertBefore($("#cardButton"+x));
    			}	
    		}	
        	 
        });

	
}

function leerColumnaBuscador(id){
	
	var url = '../Columna?board_id='+id;
    let configs = {
            method: 'get',
            withCredentials: true,
            credentials: 'include',
            headers: {
                'Content-type': 'application/json'
            }
    }
    fetch(url, configs)
        .then(res => res.json())
        .then(data => {
        	console.log(data)
        	let arrayColumn = data.response;
        	
    		if(data.status == 200){
    			for (var i = 0; i < arrayColumn.length; i++){
	    			console.log("column_id "+i+": "+arrayColumn[i].column_id);
	    			leerTarjetaBuscador(arrayColumn[i].column_id, i);
	    			var column=`
	    				<div class="column" ondrop="drop(event)" ondragover="allowDrop(event)">
							<div class="column-header" ><input id="header${i}" type="text" class="form-control" value="${arrayColumn[i].column_name}"/>
							</div>
	    				</div>`
	    				$(column).insertBefore($("#card-crear").parent());
    			}	
    		}
        });	

}

function leerTarjetaBuscador(t, x){
	
	var url = '../Tarjetas?column_id='+t;
    let configs = {
            method: 'get',
            withCredentials: true,
            credentials: 'include',
            headers: {
                'Content-type': 'application/json'
            }
    }
    fetch(url, configs)
        .then(res => res.json())
        .then(data => {
        	console.log(data)
        	let arrayCard = data.response;
        	
    		if(data.status == 200){
    			for (var i = 0; i < arrayCard.length; i++){
        			console.log("board id "+i+": "+arrayCard[i].card_id);	
        			var card = `
        				<div class="card" draggable="true" ondragstart="drag(event)">
        				<div class="card-header"> <input type="text" id="c-header" class="form-control" value="${arrayCard[i].card_name}" placeholder="Titulo"/></div>
        					<div class="card-body">
        						<textarea id="card-a" class="form-control" type="text" name="card" placeholder="Escriba algo aqui" >${arrayCard[i].card_description}</textarea>
        						<a class="edit-card" title="Editar" data-toggle="tooltip" onclick="habilitarN(this);"></a>
        					</div>
        				</div>
        			`
        				$(card).insertAfter($("#header"+x));
    			}	
    		}	
        	 
        });

	
}

function leerInvitado(t){
	
	var url = '../Invitados?board_id='+t;
    let configs = {
            method: 'get',
            withCredentials: true,
            credentials: 'include',
            headers: {
                'Content-type': 'application/json'
            }
    }
    fetch(url, configs)
        .then(res => res.json())
        .then(data => {console.log(data)
        	let arrayBuscar = data.response;
        
			if(data.status == 200){
				for (var i = 0; i < arrayBuscar.length; i++){
            		console.log("Nombre del invitado "+i+": "+arrayBuscar[i].user_username );
	            	var invitado = `	
	            	<div class="container" ><div class="card-header" style="width:100px;">${arrayBuscar[i].user_username}</div>
						<a class="delete-column" title="Borrar Invitado" data-toggle="tooltip" onclick="borrarInvitado(${arrayBuscar[i].user_id})"><img src="../img/delete.png" style="width:25px; hight:25px;"></a>
						<button id="modificar" type="button" onclick="goToUpdateInvited(${arrayBuscar[i].user_id}, ${arrayBuscar[i].type_board_user_id})" class="btn add-column"  data-toggle="modal" data-target="#estadoInvitado"><img src="../img/update.png" style="width:25px; hight:25px;"></button>
	    			</div>`
	            		$(invitado).insertAfter($("#aqui").parent());
            	}
            }
        });
	
}

function leerComentario(t){
	
	var url = '../Comentarios?card_id='+t;
    let configs = {
            method: 'get',
            withCredentials: true,
            credentials: 'include',
            headers: {
                'Content-type': 'application/json'
            }
    }
    fetch(url, configs)
        .then(res => res.json())
        .then(data => {console.log(data)
        	let arrayComment = data.response;
        
			if(data.status == 200){
				for (var i = 0; i < arrayComment.length; i++){
            		console.log("Nombre del invitado "+i+": "+arrayComment[i].user_username );
	            	var comment = `	
	            	<a class="delete-card" title="Delete Card" data-toggle="tooltip" onclick="borrarComentario(${arrayComment[i].comment_id})">
						<img src="../img/delete.png" style="width:25px; hight:25px;"/>
	            		</a>
	            	<div class="card" draggable="true" ondragstart="drag(event)">
	            		
        				<div class="card-header">${arrayComment[i].user_username}</div>
    					<div class="card-body">
	            		${arrayComment[i].comment_text}
    					</div>
        			</div>`
	            		document.getElementById("comments").innerHTML += comment; 
            	}
            }
        });
	
}

function leerArchivo(t){
	
	var url = '../SubirArchivo?card_id='+t;
    let configs = {
            method: 'get',
            withCredentials: true,
            credentials: 'include',
            headers: {
                'Content-type': 'application/json'
            }
    }
    fetch(url, configs)
        .then(res => res.json())
        .then(data => {console.log(data)
        	let arrayFile = data.response;
        
			if(data.status == 200){
				for (var i = 0; i < arrayFile.length; i++){
            		console.log("Nombre del archivo "+i+": "+arrayFile[i].file_name );
	            	var comment = `	
	            	<a class="delete-card" title="Delete Card" data-toggle="tooltip" onclick="borrarArchivo(${arrayFile[i].file_id})">
						<img src="../img/delete.png" style="width:25px; hight:25px;"/>
	            	</a>
            		<div class="card" draggable="true" ondragstart="drag(event)" onclick="bajar(${arrayFile[i].file_id})">
    					<div class="card-body" >
	            		${arrayFile[i].file_name}
    					</div>
    					
        			</div>`
	            		document.getElementById("PonerFiles").innerHTML += comment; 
            	}
            }
        });
	
}

function bajar (t) {
	var url = "../BajarArchivo?id="+ t;
	
	var downloadWindow = window.open(url);
}
//----------------------------------------------------actualizar-----------------------------------------------
function actualizarTablero(t){
	userLogin = localStorage.getItem("id");
	var json ={
            tipo: "actualizar",
            board_name: document.getElementById("cardNameUpdate").value,
            board_id: t,
            id: userLogin
    }
    
    
    let configs = {
            method: 'put',
            body: JSON.stringify(json),
            withCredentials: true,
            credentials: 'include',
            headers: {
                'Content-type': 'application/json'
            }
    }
    fetch('../Tableros', configs)
        .then(res => res.json())
        .then(data => {console.log(data)
        	
    		if(data.status == 200){
    			
            }	
            
        });	
	window.location.reload(false);
}

function actualizarTipoTablero(t){
	userLogin = localStorage.getItem("id");
	var json ={
			type_board_user_desccription:($('input:radio[name=contact]:checked').val()),
            board_id: t,
            id: userLogin 
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
    fetch('../Permisologia', configs)
        .then(res => res.json())
        .then(data => {console.log(data)
        	
    		if(data.status == 200){
    			window.location.reload(false);
            }else if(data.status == 409){
            	alert("Solo los administradores pueden cambiar el tipo de tablero");
            }
            
        });	
	
}

function actualizarColumna(t){
	userLogin = localStorage.getItem("id");
	var json ={
            tipo: "actualizar",
            column_name: document.getElementById("columnNameUpdate").value,
            column_id: t,
            id: userLogin,
            board_id: Id
    }
    
    
    let configs = {
            method: 'put',
            body: JSON.stringify(json),
            withCredentials: true,
            credentials: 'include',
            headers: {
                'Content-type': 'application/json'
            }
    }
    fetch('../Columna', configs)
        .then(res => res.json())
        .then(data => {console.log(data)
        	
    		if(data.status == 200){
    			window.location.reload(false);
            }else{
            	alert("Solo es un invitado, no puede modificar columnas a menos que las creara usted");
            }	
            
        });	
	
}

function actualizarTarjeta(t){
	userLogin = localStorage.getItem("id");
	var json ={
            tipo: "actualizar",
            card_name: document.getElementById("cardNameUpdate").value,
            card_description: document.getElementById("cardDescriptionUpdate").value,
            card_id: t,
            id:userLogin,
            board_id: Id  
    }
    
    
    let configs = {
            body: JSON.stringify(json),
            withCredentials: true,
            credentials: 'include',
            headers: {
                'Content-type': 'application/json'
            }
    }
    fetch('../Tarjetas', configs)
        .then(res => res.json())
        .then(data => {console.log(data)
        	
    		if(data.status == 200){
    			window.location.reload(false);
            }else if(data.status == 409){
            	alert("Solo es un invitado, no puede modificar una tarjeta a menos que la cree usted");
            }
            
        });	
}

function actualizarInvitado(){
	userLogin = localStorage.getItem("id");
	var json ={
            tipo: "actualizar",
            type_board_user_desccription: ($('input:radio[name=estado]:checked').val()),
            user_id: idInvitado,
            board_id: Id,
            id: userLogin
    }
    
    
    let configs = {
            method: 'put',
            body: JSON.stringify(json),
            withCredentials: true,
            credentials: 'include',
            headers: {
                'Content-type': 'application/json'
            }
    }
    fetch('../Invitados', configs)
        .then(res => res.json())
        .then(data => {console.log(data)
        	
    		if(data.status == 200){
    			window.location.reload(false);
            }else if(data.status == 409){
            	alert("No hay mas administradores");
            	window.location.reload(false);
            }else if(data.status == 408){
            	alert("Solo los administradores pueden cambiar el estado");
            	window.location.reload(false);
            }
            
        });	
}

//-------------------------------------------------------borrar----------------------------------------------------
function borrarTablero(t){
	userLogin = localStorage.getItem("id");
	boardId = t;
	var json ={
            board_id: boardId,
            id: userLogin
    }
    
    
    let configs = {
            method: 'delete',
            body: JSON.stringify(json),
            withCredentials: true,
            credentials: 'include',
            headers: {
                'Content-type': 'application/json'
            }
    }
    fetch('../Tableros', configs)
        .then(res => res.json())
        .then(data => {console.log(data)
        	let arrayColumn = data.arrayColumn;
        	
    		if(data.status == 200){
                /*localStorage.setItem('columna', JSON.stringify(arrayColumn));
                localStorage.setItem('columndId', JSON.stringify(arrayColumn.column_id));
                localStorage.setItem('columnName', JSON.stringify(arrayColumn.column_name));*/
    			window.location.reload(false);
            }else if(data.status == 409){
            	alert("Debe quedar un administrador");
            }else if(data.status == 500){
            	
            }
    		
            
        });	
}

function borrarColumna(t){
	userLogin = localStorage.getItem("id")
	var json ={
            column_id: t,
            board_id: Id,
            id: userLogin
    }
    
    
    let configs = {
            method: 'delete',
            body: JSON.stringify(json),
            withCredentials: true,
            credentials: 'include',
            headers: {
                'Content-type': 'application/json'
            }
    }
    fetch('../Columna', configs)
        .then(res => res.json())
        .then(data => {console.log(data)
        	let arrayColumn = data.arrayColumn;
        	
    		if(data.status == 200){
    			console.log(data);
    			window.location.reload(false);
            }else if(data.status == 409){
            	alert("Solo es un invitado, no puede borrar columnas a menos que las creara usted");
            }	
            
        });	
	
}

function borrarTarjeta(t){
	boardId = localStorage.getItem("columnId");
	userLogin = localStorage.getItem("id");
	var json ={
            card_id: t,
            id: userLogin, 
            board_id: Id
    }
    
    
    let configs = {
            method: 'delete',
            body: JSON.stringify(json),
            withCredentials: true,
            credentials: 'include',
            headers: {
                'Content-type': 'application/json'
            }
    }
    fetch('../Tarjetas', configs)
        .then(res => res.json())
        .then(data => {console.log(data)
        	let arrayColumn = data.arrayColumn;
        	
    		if(data.status == 200){
    			window.location.reload(false);
            }else if(data.status == 409){
            	alert("Solo es un invitado, no puede borrar una tarjeta a menos que la cree usted");
            }	
            
        });	
}

function borrarInvitado(t){
	userLogin = localStorage.getItem("id");
	var json ={
            user_id: t,
            id: userLogin,
            board_id: Id
    }
    
    
    let configs = {
            method: 'delete',
            body: JSON.stringify(json),
            withCredentials: true,
            credentials: 'include',
            headers: {
                'Content-type': 'application/json'
            }
    }
    fetch('../Invitados', configs)
        .then(res => res.json())
        .then(data => {console.log(data)
        	let arrayColumn = data.arrayColumn;
        	
    		if(data.status == 200){
    			console.log(data);
    			window.location.reload(false);
            }else if(data.status == 408){
    			alert("Solo los administradores pueden borrar a los invitados");
            }else if(data.status == 409){
    			alert("Debe quedar un administrador");
            }	
            	
            
        });	
	
}

function borrarComentario(t){
	userLogin = localStorage.getItem("id");
	var json ={
            id: userLogin,
            comment_id: t
    }
    
    
    let configs = {
            method: 'delete',
            body: JSON.stringify(json),
            withCredentials: true,
            credentials: 'include',
            headers: {
                'Content-type': 'application/json'
            }
    }
    fetch('../Comentarios', configs)
        .then(res => res.json())
        .then(data => {console.log(data)
        	let arrayColumn = data.arrayColumn;
        	
    		if(data.status == 200){
    			console.log(data);
    			window.location.reload(false);
            }	
            
        });	
	
}

function borrarArchivo(t){
	userLogin = localStorage.getItem("id");
	var json ={
			board_id: Id,
            id: userLogin,
            file_id: t
    }
    
    
    let configs = {
            method: 'delete',
            body: JSON.stringify(json),
            withCredentials: true,
            credentials: 'include',
            headers: {
                'Content-type': 'application/json'
            }
    }
    fetch('../SubirArchivo', configs)
        .then(res => res.json())
        .then(data => {console.log(data)
        	let arrayFile = data.arrayFile;
        	
    		if(data.status == 200){
    			console.log(data);
    			window.location.reload(false);
            }	
            
        });	
	
}

//---------------------------------------------Buscador---------------------------------------------------------------
function buscar(){
	userLogin = localStorage.getItem("id");
    let configs = {
            method: 'get',
            withCredentials: true,
            credentials: 'include',
            headers: {
                'Content-type': 'application/json'
            }
    }
	
	var url = '../Buscador?board_name='+document.getElementById("buscador").value+'?id='+userLogin

    fetch(url, configs)
    	.then( r => r.json() )
        .then(data => {console.log(data)
        	let arrayBuscar = data.response;
            if(data.status == 200){
            	console.log("Todo bien");
            	for (var i = 0; i < arrayBuscar.length; i++){
            		console.log("board id "+i+": "+arrayBuscar[i].board_id);	        			
            		let container = `
            			<div class="col-md-4" style=" width: 250px">
            				<div onclick="goToBuscador(${arrayBuscar[i].board_id})" onclass="card w-40" style="width: inherit; margin-top:auto;">
                				<div class="card-header text-center"> 
            						<br>
            						${arrayBuscar[i].board_name}
            						<div class="card-body"></div>
            						<br>
            					</div>
            				</div>	
            			</div>`;
            		console.log("aqui");
                	document.getElementById("board").innerHTML += container; 

            	}
            	localStorage.setItem('datos', JSON.stringify(arrayBuscar));
            }else if(data.status == 409){
            	alert("El usuario no existe");
            }else if(data.status == 201){
        		console.log("status 201 del buscador");
        		for(var i = 0; i < arrayBuscar.length; i++){
        		let container = `
        			<div class="col-md-4" style=" width: 250px">
        				<button id="modificar" type="button" onclick="goToUpdate(${arrayBuscar[i].board_id})" class="btn btn-info add-new" style="background-color: #1b9891;" data-toggle="modal" data-target="#update"><img src="../img/plus.png" style="width:30px; height:30px;" >
							Modificar
						</button>
						<button id="eliminar" type="button" onclick="borrarTablero(${arrayBuscar[i].board_id})" class="btn btn-info add-new" style="background-color: #1b9891;" data-toggle="modal" ><img src="../img/delete.png" style="width:30px; height:30px;">
						</button>
        				<div onclick="goToDetails(${arrayBuscar[i].board_id})" onclass="card w-40" style="width: inherit; margin-top:auto;">
	        				<div class="card-header text-center">  
	        					
								<br>
								${arrayBuscar[i].board_name}
        						<div class="card-body"></div>
								<br>
        					</div>
        				</div>	
        			</div>`;
        		console.log("aqui");
	        	document.getElementById("board").innerHTML += container;
        		}
            }
        });
}



function agregarInvitado(t){
	userLogin = localStorage.getItem("id");
	var json ={
            board_id: t,
            user_username: document.getElementById("username").value,
            id: userLogin
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
    fetch('../Invitados', configs)
        .then(res => res.json())
        .then(data => {console.log(data)
        	let userData = data.userData;
            if(data.status == 200){
            	console.log("Todo bien");
            	window.location.reload(false);
            }else if(data.status == 409){
            	alert("El usuario no existe");
            }
        });
}

function crearArchivo(t){
	userLogin = localStorage.getItem("id");
	var formData = new FormData();
	formData.append("file", document.getElementById("file").files[0]);
	myFile = document.getElementById("file").files[0].name;
	formData.append("user_id", userLogin);
	formData.append("card_id", t);
	formData.append("file_name", myFile);
	
	let configs = {
            method: 'post',
            body: formData,
            withCredentials: true,
            credentials: 'include'
    }
    fetch('../SubirArchivo', configs)
        .then(res => res.json())
        .then(data => {console.log(data)
        	let userData = data.userData;
            if(data.status == 200){
            	console.log("Todo bien");
            	window.location.reload(false);
            }else if(data.status == 409){
            	alert("El archivo ya existe en la tarjeta");
            }
        });
	
	
}






	





