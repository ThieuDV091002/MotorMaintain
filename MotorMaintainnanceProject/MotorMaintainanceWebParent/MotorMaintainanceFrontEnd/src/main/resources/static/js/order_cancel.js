var cancelModal;

$(document).ready(function() {
	cancelModal = $("#cancelOrderModal")
	
	$(".linkCancelOrder").on("click", function(e) {
		e.preventDefault()
		handleCancelOrderRequest()
	})
});
function handleCancelOrderRequest() {
	
	cancelModal.modal()
}