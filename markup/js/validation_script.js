var arrValid = new Array();
function statusValid(obj, error){	
	    var filter = '[for*=' + obj.substr(1) + ']';
		var cond = $(filter);
		var errText = $('label').filter(cond);
		
		if (error == 'required'){
			$(obj).keyup(function(){
				if (($(this).val() == '') || ($(this).val() == 'undefined')){	
					showError(errText, $(this));
				}
				else {
					removeError(errText, $(this));
				}
			});
			
		}
		if (error == 'date'){			
			$(obj).keyup(function(){
				if (!/([0-2]\d|3[01])\/(0\d|1[012])\/(\d{4})/.test($(this).val()) || $(this).val().length!=10){
					showError(errText, $(this));
				}
				else if ($(this).val().length == 10){
					removeError(errText, $(this));
				}
		    });
			$(obj).change(function(){
				if (!/([0-2]\d|3[01])\/(0\d|1[012])\/(\d{4})/.test($(this).val()) || $(this).val().length!=10){
					showError(errText, $(this));
				}
				else if ($(this).val().length == 10){
					removeError(errText, $(this));
				}
		    });
		}
		if (error == 'time'){
			$(obj).keyup(function(){
				if (!/^(([0,1][0-9])|(2[0-3])):[0-5][0-9]$/.test($(this).val())){
					showError(errText, $(this));
				}
				else{
					removeError(errText, $(this));
				}
			});
			
		}
}
function protectHTML(str){
		str = str.replace(new RegExp("<", 'g'), "&lt;");
		str = str.replace(new RegExp(">",'g'), '&gt;');
		str = str.replace(new RegExp("\"", 'g'), "&quot;");
		return str;
}
function isValidForm(){
	var count = 0;
	for(var key in arrValid){
		if (arrValid[key] == false)
			count++;
	}
	return (count == 0);
}
function showError(errText, obj){
	$(errText).show(); 
	obj.addClass('invalid');
	arrValid[obj.attr('id')] = false;
	$('button[type="submit"]').hide();
	
}
function removeError(errText, obj){
	$(errText).hide(); 
	obj.removeClass('invalid');
	arrValid[obj.attr('id')] = true;
	if (isValidForm())
		$('button[type="submit"]').show();
}
$(document).ready(function(){
	$('.error').hide();
	statusValid('#meet_name', 'required');
	statusValid('#meet_descr','required');
	statusValid('#datepicker', 'date');
	statusValid('#meet_time', 'time');
});