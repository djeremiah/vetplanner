function solve(){
    $.get("/solve", data => {
        $("#schedule-button").off().text("Show Schedule").on("click", showSolution);
    });
}

function showSolution(){
    $.get("/solution", data => {
        data.forEach(visit => {
            $("#visit-" + visit.holding.id + "-" + visit.vet.id).addClass("pf-m-blue").children().text(visit.startingTimeGrain?.dateTimeString ?? "unscheduled");
        });
        $("#schedule-button").off().text("Reset").on("click", reset);
    });
}

function reset(){
    $.get("/stop", data => {
        $("span[id^=visit]").removeClass("pf-m-blue").children().text("unscheduled");
        $("#schedule-button").off().text("Solve").on("click", solve);
    });
}

$(document).ready(() => {
    $("#schedule-button").on("click", solve);
});