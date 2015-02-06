/**
 * Created by Gleb on 1/31/2015.
 */
$(document).ready(function () {
	$("#g-ok").on('click', function () {
		$(this).toggleClass("glyphicon-ok");
		$(this).toggleClass("glyphicon-remove");
	});
});

/** table sorting */
$(document).ready(function () {
	$("#meal-table").tablesorter({
		headers: {
			3: {sorter: false},
			4: {sorter: false}
		}
	});
});

/** add Like */
$(document).ready(function () {
	$(".plus-like").click(function () {
		/*   jsRoutes.controllers.Application.addDish().ajax();
		 console.log("jquery called");*/
		var id = $(this).closest("tr").attr("database-id");

		var likeButton = $(this).find("span.glyphicon");
		var oldval = +likeButton.text().trim();
		$.ajax(jsRoutes.controllers.MealsController.addLike(+id))
			.done(function () {
				var newval = oldval + 1;
				likeButton.text(" " + newval + " ");
			})
			.fail(function () {
				console.log("failed")
			});
	});
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

//$(document).ready(function () {
//    $(".btn-group").click(function () {
//        var row = $(this).closest("tr");
//        var id = +row.attr("database-id");
//        $.ajax(jsRoutes.controllers.MealsController.deleteDish(id))
//            .done(function () {
//                row.remove();
//            })
//            .fail(function () {
//                console.log("failed")
//            });
//    });
//});

$(document).ready(function () {
	$("#sort-button").on('click', function () {
		var icon = $(this).find("span");
		icon.toggleClass("glyphicon-chevron-up");
		icon.toggleClass("glyphicon-chevron-down");
		/* sorting */
		var ord = $(this).val();

		var newValue = ord == "asc" ? "desc" : "asc";
		console.log("value changed to " + newValue);
		$(this).val(newValue);
	});
});
