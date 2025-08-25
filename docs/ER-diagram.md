```mermaid
erDiagram
    erDiagram
    ROUTINE {
        int id PK
        string name "NN"
    }

    TASK {
        int id PK
        string name "NN"
    }

    STEP {
        int id PK
        string name "NN"
        int taskId FK "NN"
        int position "NN"
    }

    EXECUTION {
        int id PK
        int parentExecutionId FK
        int parentRoutineId FK
        int stepId FK "NN"
        int position "NN"
        int start "NN"
        int end
    }

    ROUTINE 0+ to 0+ TASK: groups
    TASK 1 to 0+ STEP: consists_of
    STEP 1 to 0+ EXECUTION: produces
    EXECUTION 0+ to zero or one ROUTINE: belongs_to
```