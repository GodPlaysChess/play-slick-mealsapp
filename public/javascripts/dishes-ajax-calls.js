
/** table sorting */
$(document).ready(function () {
	$("#meal-table").tablesorter();
});

/** remove entry */
$(document).ready(function () {
	$(".remove-dish").click(function () {
		var row = $(this).closest("tr");
		var id = +row.attr("database-id");
		$.ajax(jsRoutes.controllers.MealsController.deleteDish(id))
			.done(function () {
				row.remove();
			})
			.fail(function () {
				console.log("failed to delete " + id)
			});
	});
});

/** evaluate */
$(document).ready(function () {
	$('.rating').on('rating.change', function (event, value) {
		console.log(value);
		var row = $(this).closest("tr");
		var id = +row.attr("database-id");
		$.ajax(jsRoutes.controllers.MealsController.rateDish(id, value));
		// It is really not neccessary to update average before in place, cause the vital information for user is the
		// avg BEFORE evaluation.
		console.log(value);
	});
});

