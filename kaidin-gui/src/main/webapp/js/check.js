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
	$("#" + codeimgId).attr("src", url);
}

function showError(errCode, errMsg) {
	if ("undefined" == typeof(errMsg) && '' == errMsg) {
		return;
	}
	var errContent = '';
	if ("undefined" != typeof(errCode) && '' != errCode) {
		errContent = "errCode:" + errCode;
		if(!document.all) {
			// FF谷歌浏览器用这个
			errContent += '\n';
		} else {
			// IE系列用这个
			errContent += '\r\n';
		}
	}

	errContent += 'errMsg:' + errMsg;
	alert(errContent)
}
