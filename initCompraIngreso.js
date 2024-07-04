// initialize the validator function
var frmCompras = document.getElementById("frmCompra");
var oDocumento;
		validator.message['date'] = 'not a real date';         
             // validate a field on "blur" event, a 'select' on 'change' event & a '.reuired' classed multifield on 'keyup':
             $('form')
			.on('blur', 'input[required], input.optional, select.required', validator.checkField)
			.on('change', 'select.required', validator.checkField);
                     function detallesOrdinarios(){
			var submit = true;
                        var datosCompra=[{name:"eve", value: "0"}];
			// evaluate the form using generic validaing                        
			if( !validator.checkAll( $(this) ) ){
			   submit = false;                  
                           if(frmCompras.funcion.value=="editar"){
                              alert("Disculpe, faltan atributos requeridos para la visualización de la factura de compra");
                              return false;
                           }else{
                              alert("Disculpe, la peticion no se envia al servidor debido a que faltan atributos requeridos para el ingreso del documento");
                              return false;
                           }
			}
                        var respValid = compFechaFactura();
                        if(respValid != "v") {
                           alert(respValid);
                           return false
                        }                        
                        var pos = frmCompras.clienteCompra.options.selectedIndex;                       
                        var elem = frmCompras.clienteCompra.value.split("-");
                        datosCompra.push({value: frmCompras.clienteCompra.options[pos].text});
                        datosCompra.push({value: frmCompras.descripToProveedor.value});
                        datosCompra.push({value: frmCompras.diaCompra.value+"/"+frmCompras.mesCompra.value+"/"+frmCompras.anoCompra.value});
                       // la variable cancelado es la que desencadena o no frmCompramenuprincipal
			if(submit == true && frmCompras.funcion.value=="ingresar"){
                           if(frmCompras.mesCompra.value!=frmCompras.mesTrabajo.value && frmCompras.tipoCompra.value=="Ordinal"){
                              alert("Disculpe, el mes  de la factura a ingresar no coincide con el mes de trabajo del cliente: "+frmCompras.clienteCompra.options[pos].text); return false;
                            }
                            if(frmCompras.mesCompra.value!=frmCompras.mesTrabajo.value && frmCompras.tipoCompra.value=="NCR"){
                              alert("Disculpe, el mes  de la Nota de Credito a ingresar no coincide con el mes de trabajo del cliente: "+frmCompras.clienteCompra.options[pos].text); return false;
                            }
                            datosCompra.push({value: frmCompras.compraFacturacion.value});
                            datosCompra.push({value: frmCompras.compraControl1.value+"-"+frmCompras.compraControl2.value});
                            datosCompra.push({value: elem[0]});
                            datosCompra.push({value: frmCompras.idProveedor.value});
                            datosCompra.push({value: elem[1]});
                            datosCompra.push({value: frmCompras.tipoCompra.value});
                            datosCompra.push({value: frmCompras.mesAjusteCompra.value});
                            datosCompra.push({value: frmCompras.anoTrabajo.value});
                            datosCompra.push({value: "0"});
                            datosCompra.push({value: "0"});
                            datosCompra.push({value: "0"});
                            datosCompra.push({value: frmCompras.condicionPago.value});
                            datosCompra.push({value: frmCompras.diaVenc.value+"/"+frmCompras.mesVenc.value+"/"+frmCompras.anoVenc.value});
                            frmCompras.compraFacturacion.value="";
                            frmCompras.compraControl2.value="";
                            frmCompras.compraControl1.value="00";
                            if(frmCompras.tipoCompra.value=="NCR" || frmCompras.tipoCompra.value=="NCRA" ){                               
                              var compIngNCR=showModalDialog("administrativo_compra_NCR_llenado.html",datosCompra,"top=5,left=24,resizable=no,toolbar=0,location=0");
                           }else{
                              var compIngFac=showModalDialog("administrativo_compra_llenado.html",datosCompra,"top=5,left=24,resizable=no,toolbar=0,location=0");
                           }
                        }
                        cancelado="nada";fase="segundo";
		}

function busqDocumentoExistente(txtDocumento,contexto){
 if(frmCompras.idProveedor.value=="301370139"){
      var preFac = txtDocumento.value.substring(0, 3);
      if(preFac =="722"){ txtDocumento.value="V029"+txtDocumento.value; }
      if(preFac =="798"){ txtDocumento.value="V077"+txtDocumento.value; }
   }
 if(frmCompras.compruebaCompra.value=="false" && contexto=='control'){
     return false;
  }
 if(txtDocumento.value.length>2 && frmCompras.idProveedor.value.length>2){
   var entidad = "BusqFacturasExIntranet";
   var cadenaAtributos, streaming="";
   oDocumento= contexto;
   if(contexto=='control') cadenaAtributos =contexto+"','"+frmCompras.compraControl1.value+"-"+frmCompras.compraControl2.value+"','"+frmCompras.idProveedor.value;
   else cadenaAtributos = contexto+"','"+frmCompras.compraFacturacion.value+"','"+frmCompras.idProveedor.value;
   streaming= entidad + "|" + cadenaAtributos;
   doAjax('/Modulo-Administracion/Administrativo.do',streaming,'respBusqFacturasExistentes','get',0);
 }
}
function respBusqFacturasExistentes(resp){
 if(resp!="0"){
   var atribFac = resp.split(";");
   var fFactura = new Date();
   fFactura.setTime(atribFac[0]);
   var mes=fFactura.getMonth()+1;
   if(mes.toString().length==1) mes= "0"+mes;
   if(fFactura.getDate().toString().length==1) dia = "0"+fFactura.getDate();
   else dia = fFactura.getDate();
   document.getElementById("tipoContexto").innerHTML=" Tipo Contexto: "+atribFac[4];
   document.getElementById("fechaConsulta").innerHTML=" Fecha Factura: "+dia+"/"+mes+"/"+fFactura.getFullYear();
   document.getElementById("facturaConsulta").innerHTML="  Nro. Factura: "+atribFac[1];
   document.getElementById("controlConsulta").innerHTML="  Nro. Control: "+atribFac[2];
   document.getElementById("baseConsulta").innerHTML= "Base Imponible: "+mascaraMillar(atribFac[3]);
   var oCapa = document.getElementById("fondoAsociasionCompra");
   oCapa.setAttribute("class", "fondoFuncion");
   var oCapCompra = document.getElementById("capaDocumentoEncontado");
   oCapCompra.style.display='block';
   }
}
function compFechaFactura(){
 if(frmCompras.mesCompra.value.length<1) return "Disculpe, el mes introducido para el documento no es valido";
 if(frmCompras.diaCompra.value.length<1) return "Disculpe, el dia introducido para el documento no es valido";
 var valorN = new Number(frmCompras.anoCompra.value);
 if(valorN <=2000)return "Disculpe, el año introducido para el documento no es valido";
 if(valorN > parseInt(frmCompras.anoTrabajo.value) ) return "Disculpe, el año introducido es superior al año de trabajo del cliente ";
 var valorN = frmCompras.mesCompra.value;
 if(valorN >12)return "Disculpe, el mes introducido para el documento no es valido";
 if(valorN<1 )return "Disculpe, el mes introducido para el documento no es valido";
 if(frmCompras.tipoCompra.value=="Ajuste" || frmCompras.tipoCompra.value=="NCRA"){
   var mismoContexto= true;
   if(parseInt(frmCompras.anoCompra.value)== parseInt(frmCompras.anoTrabajo.value))
     mismoContexto= true;
   else
     mismoContexto= false;
   if(parseInt(frmCompras.mesCompra.value)>= parseInt(frmCompras.mesTrabajo.value) && mismoContexto== true) return "Disculpe, el mes introducido para ajustar  el documento no debe ser igual o mayor al mes de trabajo del cliente";
  }
  var fechaStd =new Date(frmCompras.anoCompra.value,frmCompras.mesCompra.value-1,01);
  var fechaValid =new Date(frmCompras.anoCompra.value,frmCompras.mesCompra.value-1,frmCompras.diaCompra.value);
  if(fechaStd.getMonth()!=fechaValid.getMonth()){
      return "Disculpe, el dia introducido para el documento no es valido";
   }
   return "v";
}
function mostrarAtributosCompra(){
   var datosCompra=[{name:"eve", value: "0"}];  
   var pos = frmCompras.clienteCompra.options.selectedIndex;
   var elemCmp =frmCompras.compraFacturacion.value.split("!");
   if(parseInt(elemCmp[7])==  parseInt(document.getElementById("mesTrabajo").value)-1 &&  parseInt(elemCmp[8]) == parseInt(document.getElementById("anoTrabajo").value) ){
      datosCompra.push({value: frmCompras.clienteCompra.options[pos].text});
      datosCompra.push({value: frmCompras.descripToProveedor.value});
      datosCompra.push({value: frmCompras.diaCompra.value+"/"+frmCompras.mesCompra.value+"/"+frmCompras.anoCompra.value});
      datosCompra.push({value:elemCmp[0]});// numero compra
      datosCompra.push({value:elemCmp[1]});// factura
      datosCompra.push({value:elemCmp[2]}); // control
      datosCompra.push({value:elemCmp[5]});// tipoCompra
      datosCompra.push({value:elemCmp[3]}); // base imponible
      datosCompra.push({value:elemCmp[4]});// exento
      datosCompra.push({value:elemCmp[6]});// mes ajuste, si aplica
      datosCompra.push({value:frmCompras.tipoCompra.value});//tipo de compra
      clearAsignacionCompras(frmCompras.compraFacturacion);
      var compEditAtrr=showModalDialog("administrativo_compra_edicion_atributo.html",datosCompra,"top=5,left=24,resizable=no,toolbar=0,location=0");
   }else{ alert("Disculpe, el documento que usted intenta editar no se encuentra dentro del contexto de trabajo actual del cliente");}
}
                $(document).ready(function(){
                   buscarAllOrdinario();
                   var year = new Date();
                   $("#diaCompra").mask("99");
                   var datas =[{value: "pelote", name: "Inicializacion"}];
                   datas.push({value:"pelo"})
                   $("#compraProveedores").suggestComprasIng(datas, {asHtmlID:"descripcion",selectionLimit:1,selectedItemProp: "name", searchObjProps: "name",startText: "Busq. Proveedores ..!",emptyText: "No hay Proveedores registrados con esta Descripción",minChars: 3,selectionLimit:1,selectionAdded: function(elem){seleccionProveedores(elem);}, selectionRemoved: function(elem){ busqProveedores(elem);}});
                });
function buscarAllOrdinario(){
    var entidad = "clienteAllOrdinario";
    var streaming=entidad;
    doAjax('/Modulo-Administracion/Administrativo.do',streaming,'respAllClienteOrdinario','get',0);
}function respAllClienteOrdinario(resp){
    var fechaTrabajo = new Date();
   // fechaTrabajo.setTime();
    var datosClientes= resp.split("|");
    var oSeletClientes = document.getElementById("clienteCompra");
    if(datosClientes.length == 1){
        alert("Disculpe, no se consiguieron clientes para poder cargarle esta compra");
    }
    var trans="";
    var objOPt = document.createElement("option");
    oSeletClientes.appendChild(objOPt);
    for(var i=0, len=datosClientes.length; i< len -1;i++){
        var objOPt = document.createElement("option");
        var atributos = datosClientes[i].split(";");
        trans=atributos[1].replace(/^\s+|\s+$/g,"")+"-"+atributos[5]+"-"+atributos[14]+"-"+atributos[15];
   
        objOPt.value= trans;
        trans=atributos[2].replace(/^\s+|\s+$/g,"");
        objOPt.text= trans;
        oSeletClientes.appendChild(objOPt);
    }
}
function validacionEntFecha(fecha){
  var fCadena = fecha.value.split("/");
  var validado = false;
  var year = new Date(fCadena[2],fCadena[1]-1,fCadena[0]);
  if(year.getFullYear()== fCadena[2] && year.getMonth()==fCadena[1]-1&& year.getDate()==fCadena[0]){
     validado = true;
  }
  if(validado==false){
     alert("Disculpe, la fecha de compra introducida no es valida");
     fecha.focus();
  }
}
function mostrarAjuste(obj){
  var oCapa = document.getElementById("mesAjuste");
  if(obj.value=="Ajuste" ||obj.value=="NCRA"  ){
     oCapa.style.display='block';
     frmCompras.mesCompra.disabled=false;
     frmCompras.anoCompra.disabled=false;
     frmCompras.diaCompra.focus();
  }else{
     oCapa.style.display='none';
     frmCompras.mesCompra.disabled=true;
     frmCompras.anoCompra.disabled=true;
     frmCompras.anoCompra.value= frmCompras.anoTrabajo.value;
     frmCompras.mesCompra.value= frmCompras.mesTrabajo.value;
  }
}
function mostarVencimiento(obj){
   var oCapa = document.getElementById("capaFechaVenc");
  if(obj.value=="1" ){
     oCapa.style.display='block';

  }else{
     oCapa.style.display='none';
  }
}
function clearAsignacionCompras(oSelet){
    var pos= oSelet.length;
    for(var m= 0,lngrelacion=oSelet.length; m< lngrelacion ;m++){
        oSelet.remove(pos);
        pos--;
    }
    oSelet.remove(0);
}function busquedasCompras(){ 
   var idProv =document.getElementById("idProveedor");
   var idClt =document.getElementById("clienteCompra");
   var entidad = "compraAsociadasClienteProveedor";
   var respValid = compFechaFactura();
   if(respValid != "v") {
      alert(respValid);
      return false
   }
   var fecha = new Date(parseInt(frmCompras.anoCompra.value),parseInt(frmCompras.mesCompra.value)-1,parseInt(frmCompras.diaCompra.value));
   var elemClientes= idClt.value.split("-");
   cadenaAtributos = elemClientes[0]+"'\'"+idProv.value+"'\'"+fecha.getTime()+"'\'"+frmCompras.tipoCompra.value;
   streaming= entidad + "|" + cadenaAtributos;
      doAjax('/Modulo-Administracion/Administrativo.do',streaming,'resBusqComprasEncontradas','get',0);
  // }else alert("Disculpe, no he podido buscar compras existentes debido a que me faltan atributos ");
}
function resBusqComprasEncontradas(resp){
var datosCompra= resp.split("&");
 var fechaAjuste = new Date();
 var fechaTrabajo = new Date();
    var oSeletCompras= document.getElementById("compraFacturacion");
    clearAsignacionCompras(oSeletCompras);
    if(datosCompra.length == 1){
        alert("Disculpe, no se consiguieron compras asignadas para este cliente asocida con este proveedor");
        return false;
    }
    var objOPt = document.createElement("option");
    oSeletCompras.appendChild(objOPt);
    objOPt.text= "Documento(s) Encontrados Asociados a este Proveedor ";
    objOPt.value="";
    var trans="";
    var etiqueta;
    var idRetencion;
    for(var i=0, len=datosCompra.length; i< len -1;i++){
        var objOPt = document.createElement("option");   
        var atributosCompra = datosCompra[i].split("!");
        fechaAjuste.setTime(atributosCompra[8]);
        fechaTrabajo.setTime(atributosCompra[1]);
        if(atributosCompra[7]=="O") idRetencion=atributosCompra[8];
        else  idRetencion=atributosCompra[9]
        trans=atributosCompra[0]+"!"+atributosCompra[3]+"!"+atributosCompra[4]+"!"+atributosCompra[5]+"!"+atributosCompra[6]+"!"+atributosCompra[7]+"!"+fechaAjuste.getMonth()+"!"+ fechaTrabajo.getMonth()+"!"+ fechaTrabajo.getFullYear()+"!"+idRetencion;
        objOPt.value= trans;
        etiqueta=atributosCompra[3];
        trans=" DOC: "+atributosCompra[0]+"Tipo: "+atributosCompra[2]+", Nro. "+etiqueta +"  Base Imp."+atributosCompra[5]+" Bs."
        objOPt.text= trans;
        oSeletCompras.appendChild(objOPt);
    }
}
function eliminacionFacturaCompra(){
  var oSeletCompras= document.getElementById("compraFacturacion");
  var oSeletClientes = document.getElementById("clienteCompra");
  if(oSeletCompras.value==""){
    alert("Disculpe, no se ha seleccionado ningún Documento para su eliminación");
  }
  var idCompra=  oSeletCompras.value.split("!"); 
  if(parseInt(idCompra[9])>0){ alert("Disculpe, el Documento actual que se intenta eliminar, posee actualmente un Comprobante de Retencion vinculado a el, por lo tanto no se puede derogar"); return false;}
  if(parseInt(idCompra[7])==  parseInt(document.getElementById("mesTrabajo").value)-1 &&  parseInt(idCompra[8]) == parseInt(document.getElementById("anoTrabajo").value) ){
     var rifCliente =  oSeletClientes.value.split("-");
     var entidad = "eliminacionFacturaCompra";
     cadenaAtributos = idCompra[0] + "!" + rifCliente[0] ;
     streaming= entidad + "|" + cadenaAtributos;
     doAjax('/Modulo-Administracion/Administrativo.do',streaming,'respEliminacionFactura','get',0);
  }else{ alert("Disculpe, el documento que usted intenta eliminar no se encuentra dentro del contexto de trabajo actual del cliente");}
}
function respEliminacionFactura(resp){
  var oSeletCompras= document.getElementById("compraFacturacion");  
  alert("Se ha eliminado la factura de compra con exito");
  oSeletCompras.remove(oSeletCompras.selectedIndex);
}
function estadoCliente(oSeletClientes){
 var datosEstados =  oSeletClientes.value.split("-");  
 var fechaTrabajo = new Date();
 fechaTrabajo.setTime(datosEstados[3]); 
 var mesTemp= new String(fechaTrabajo.getMonth()+1);
 var anoTemp= new String(fechaTrabajo.getFullYear());
 var mesAjuste = document.getElementById("mesAjusteCompra");
 mesAjuste.selectedIndex= mesTemp;
 if (mesTemp.length==1) mesTemp="0"+mesTemp;
 document.getElementById("mesTrabajo").value=mesTemp;
 document.getElementById("mesCompra").value=mesTemp;
 document.getElementById("anoCompra").value=anoTemp;
 document.getElementById("anoTrabajo").value=anoTemp;
 frmCompras.idProveedor.value="";
 frmCompras.descripToProveedor.value=""; 
 $("#cerrar").click();
 frmCompras.idProveedor.value="";
}
function closeVentanaAsociacion(){
  if(oDocumento =="factura") frmCompras.compraFacturacion.focus();
  else frmCompras.compraControl2.focus();
  var oCapDocumento = document.getElementById("capaDocumentoEncontado");
  oCapDocumento.style.display='none';
  var oCapa = document.getElementById("fondoAsociasionCompra");
  oCapa.setAttribute("class", "");  
}
function mascaraMillar(cantida) {
 var numero = new String(cantida);
 var n=numero.indexOf(".");
 if(n<0) numero =numero +".00";
 var cntUnidades=numero.split(".");
 var posCorte;
 var strCorte;
 var strCorteP;
 var strCorteM,strCorteM2;
 var strMiles;
 var strMillon;
 if(cntUnidades[0].length>=4 && cntUnidades[0].length<=6){
     posCorte= parseFloat(cntUnidades[0].length) -parseFloat(3);
     strCorte = cntUnidades[0].slice(posCorte,cntUnidades[0].length);
     strCorteP = cntUnidades[0].slice(0,posCorte);
     strMiles = strCorteP+","+strCorte+"."+ cntUnidades[1];
     return strMiles;
 }if(cntUnidades[0].length==7){
   strMillon  =cntUnidades[0].slice(0,1);
   var strMil = cntUnidades[0].slice(1,cntUnidades[0].length);
   posCorte= parseFloat(strMil.length) -parseFloat(3);
   strCorte = strMil.slice(posCorte,strMil.length);
   strCorteP = strMil.slice(0,posCorte);
   strMiles =  strMillon +","+strCorteP+","+strCorte+"."+ cntUnidades[1];
   return strMiles;
 }else{
    return cntUnidades[0] +"."+ cntUnidades[1];
 }
}