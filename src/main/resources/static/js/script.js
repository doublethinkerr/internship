document.getElementById('createDate').valueAsDate = new Date();

document.getElementById('receiptDate').max = new Date(new Date().getTime() - new Date().getTimezoneOffset() * 60000).toISOString().split("T")[0];
document.getElementById('createDate').max = new Date(new Date().getTime() - new Date().getTimezoneOffset() * 60000).toISOString().split("T")[0];