function check() {
	var username = $("#username").value;
	if ("" == username) {
		alert("账号不能为空！");
		return false;
	}
	
	var password = $("#password").value;
	if ("" == password) {
		alert("密码不能为空！");
		return false;
	}
	
	return true;
}

function isEmpty(inputId, errorId) {
	var userName = $("#" + inputId).value;
	if ("" == userName) {
		$("#" + errorId).show();
	} else {
		$("#" + errorId).hide();
	}
}


function changeImg(codeimgId, url) {
	$("#" + codeimgId).attr("src", url + '&time=' + new Date().getTime());
}

function showError(errorMsg) {
	if ("undefined" != typeof(errorMsg) && '' != errorMsg) {
		alert(errorMsg)
	}
}
