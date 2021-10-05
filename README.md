## Problem Description
Assign each holding to a vet and a time.

### Hard Constraints
* Two holdings may not be assigned to the same vet at the same time
* Visits may not be scheduled across multiple days

### Medium Constraints
* Prefer to schedule larger holdings over smaller ones if there is a conflict

### Soft Constraints
* Finish all visits sooner rather than later


## Running
To run the demo, execute `./mvnw spring-boot:run` in the root project directory, and navigate to [http://localhost:8080](http://localhost:8080). Click "Solve" to start the solver, then "Show Solution" to display the assigned visits in Holding/Vet table. You can then click "Reset" to reset.

If the solver and UI get out of sync, you can stop solving with `curl http://localhost:8080/stop` and reload the UI.

Vets and Holdings are in [src/main/resources/vetplanner_holdings.csv](src/main/resources/vetplanner_holdings.csv) and [src/main/resources/vetplanner_vets.csv](src/main/resources/vetplanner_vets.csv), respectively. They are currently only loaded at startup, so any changes will require a restart of the spring boot server before they're picked up in the UI/Solver.

