
initGrid = function(options) {
	var gridId = options.gridId.replace(/:/g, "\\:");
	console.log("gridId: " + gridId);
	var cmp = $("#" + gridId);

	// init row selection css
	$("table tbody tr", cmp).each(function() {

		// apply the selected row css selector
		var selected = $("i.selected", this);
		$(this).toggleClass("selected-row", selected.length != 0);

		// make the row appear clickable
		$(this).css({ "cursor" : "pointer" });
	});

	if (options.onSelect) {

		// init row selection event listener
		$("table tbody", cmp).on("click", "tr", function() {
			var recordKey = $(".recordKey", this).text();
			if (recordKey) options.onSelect.call(this, recordKey);
		});
	}
};
