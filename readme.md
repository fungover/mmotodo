# GraphQL API Documentation

All graphql requests goes to `http://localhost:8080/graphql` with HTTP method `POST`
Queries are used to retrieve data and mutations are for creating, updating and removing resources.

In the request body you must pass a json object with a property query where you can pass the query or mutation you need

```json
{
    "query": "query {allTasks { id, title}}"
}
```

## Task

### Task fields

```json
{
    "id": 1,
    "title": String,
    "description": String,
    "status": String,
    "created": LocalDateTime,
    "updated": LocalDateTime,
    "dueDate": LocalDateTime,
    "timeEstimation": int,
    "category": {
        "id": int,
        "name": String,
        "description": String,
        "created": LocalDateTime,
        "updated": LocalDateTime
    },
    "tag": {
        "id": 1,
        "name": String,
        "description": string,
        "created": LocalDateTime,
        "updated": LocalDateTime
    },
}
```

### Task response

`200 OK, 201 CREATED, 202 NO-CONTENT`

```json
  {
    "data": {
      "allTasks": [
        {
            // Task fields
               ...
        },
      ]
    }
  }
```

`404 NOT_FOUND`

```json
{
    "errors": [
        {
            "message": "Task not found",
            "locations": [
                {
                    "line": 2,
                    "column": 3
                }
            ],
            "path": ["taskById"],
            "extensions": {
                "classification": "NOT_FOUND"
            }
        }
    ],
    "data": {
        "taskById": null
    }
}
```

## Queries

### Fetch all tasks

```graphql
query {
    allTasks {
       // Task fields
          ...
    }
}

// Reponds with task list
```

### Fetch task by id

```graphql
query {
    taskById(id: 1) {
       // Task fields
          ...
    }
}
```

### Fetch tasks by Tag id

```graphql
query MyQuery {
  tasksByTagId
}

// Reponds with task list
```

### Fetch tasks by category id

```graphql
  query {
    tasksByCategoryId(categoryId: 1) {
      // Task fields
          ...
    }
  }

  // Reponds with task list
```

## Mutations

### Add task

```graphql
mutation {
  addTask(
    task: {
        description: String,
        status: String,
        title: String
    }) {
      // Task fields
         ...
    }
}
```

### Delete task

```graphql
mutation {
    deleteTask(id: Int)
}

// Always responds with a string
```

### Update task

```graphql
mutation {
  updateTask(
    task: {
        categoryId: Int,
        description: String,
        id: Int,
        status: String,
        tagId: Int,
        title: String
    }) {
    // Task fields
       ...
    }
}
```
