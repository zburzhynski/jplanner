databaseChangeLog {

    changeSet(id: '2015-09-11-1', author: 'Vladimir Zburzhynski <zburzhynski@gmail.com>') {
        createTable(schemaName: 'jplanner', tableName: 'available_resource', tablespace: 'jplanner_data', remarks: 'Available resource') {
            column(name: 'id', type: 'VARCHAR(128)', remarks: 'The unique identifier of available resource') {
                constraints(nullable: false)
            }
            column(name: 'name', type: 'VARCHAR(100)', remarks: 'The available resource name') {
                constraints(nullable: false)
            }
            column(name: 'doctor_id', type: 'VARCHAR(128)', remarks: 'The reference to the employee table') {
                constraints(nullable: false)
            }
            column(name: 'assistant_id', type: 'VARCHAR(128)', remarks: 'The reference to the employee table')
            column(name: 'workplace_id', type: 'VARCHAR(128)', remarks: 'The reference to the workplace table') {
                constraints(nullable: false)
            }
            column(name: 'description', type: 'VARCHAR(500)', remarks: 'The available resource description')
        }
        addPrimaryKey(schemaName: 'jplanner', tableName: 'available_resource', tablespace: 'jplanner_index',
                columnNames: 'id', constraintName: 'PK_available_resource')
    }

    changeSet(id: '2015-09-11-2', author: 'Vladimir Zburzhynski <zburzhynski@gmail.com>') {
        addForeignKeyConstraint(constraintName: 'FK_doctor_2_employee',
                baseTableName: 'available_resource', baseTableSchemaName: 'jplanner', baseColumnNames: 'doctor_id',
                referencedTableName: 'employee', referencedTableSchemaName: 'jplanner', referencedColumnNames: 'id')
    }

    changeSet(id: '2015-09-11-3', author: 'Vladimir Zburzhynski <zburzhynski@gmail.com>') {
        addForeignKeyConstraint(constraintName: 'FK_assistant_2_employee',
                baseTableName: 'available_resource', baseTableSchemaName: 'jplanner', baseColumnNames: 'assistant_id',
                referencedTableName: 'employee', referencedTableSchemaName: 'jplanner', referencedColumnNames: 'id')
    }

    changeSet(id: '2015-09-11-4', author: 'Vladimir Zburzhynski <zburzhynski@gmail.com>') {
        addForeignKeyConstraint(constraintName: 'FK_available_resource_2_workplace',
                baseTableName: 'available_resource', baseTableSchemaName: 'jplanner', baseColumnNames: 'workplace_id',
                referencedTableName: 'workplace', referencedTableSchemaName: 'jplanner', referencedColumnNames: 'id')
    }

    changeSet(id: '2015-09-11-5', author: 'Vladimir Zburzhynski <zburzhynski@gmail.com>') {
        createTable(schemaName: 'jplanner', tableName: 'resource_timetable', tablespace: 'jplanner_data', remarks: 'Available resource timetable') {
            column(name: 'id', type: 'VARCHAR(128)', remarks: 'The unique identifier of resource timetable') {
                constraints(nullable: false)
            }
            column(name: 'available_resource_id', type: 'VARCHAR(128)', remarks: 'The reference to the available resource table') {
                constraints(nullable: false)
            }
            column(name: 'start_date', type: 'TIMESTAMP', remarks: 'Start date of resource timetable') {
                constraints(nullable: false)
            }
            column(name: 'end_date', type: 'TIMESTAMP', remarks: 'End date of resource timetable') {
                constraints(nullable: false)
            }
            column(name: 'status', type: 'VARCHAR(10)', remarks: 'Status of resource timetable') {
                constraints(nullable: false)
            }
            column(name: 'description', type: 'VARCHAR(500)', remarks: 'The resource timetable description')
        }
        addPrimaryKey(schemaName: 'jplanner', tableName: 'resource_timetable', tablespace: 'jplanner_index',
                columnNames: 'id', constraintName: 'PK_resource_timetable')
    }

    changeSet(id: '2015-09-11-6', author: 'Vladimir Zburzhynski <zburzhynski@gmail.com>') {
        addForeignKeyConstraint(constraintName: 'FK_resource_timetable_2_available_resource',
                baseTableName: 'resource_timetable', baseTableSchemaName: 'jplanner', baseColumnNames: 'available_resource_id',
                referencedTableName: 'available_resource', referencedTableSchemaName: 'jplanner', referencedColumnNames: 'id')
    }

    changeSet(id: '2015-09-11-7', author: 'Vladimir Zburzhynski <zburzhynski@gmail.com>') {
        createTable(schemaName: 'jplanner', tableName: 'quota', tablespace: 'jplanner_data', remarks: 'Time quota') {
            column(name: 'id', type: 'VARCHAR(128)', remarks: 'The unique identifier of quota') {
                constraints(nullable: false)
            }
            column(name: 'resource_timetable_id', type: 'VARCHAR(128)', remarks: 'The reference to the resource timetable table') {
                constraints(nullable: false)
            }
            column(name: 'start_date', type: 'TIMESTAMP', remarks: 'Start date of quota') {
                constraints(nullable: false)
            }
            column(name: 'end_date', type: 'TIMESTAMP', remarks: 'End date of quota') {
                constraints(nullable: false)
            }
            column(name: 'quota_type', type: 'VARCHAR(15)', remarks: 'The quota type') {
                constraints(nullable: false)
            }
            column(name: 'description', type: 'VARCHAR(500)', remarks: 'The quota description')
        }
        addPrimaryKey(schemaName: 'jplanner', tableName: 'quota', tablespace: 'jplanner_index',
                columnNames: 'id', constraintName: 'PK_quota')
    }

    changeSet(id: '2015-09-11-8', author: 'Vladimir Zburzhynski <zburzhynski@gmail.com>') {
        addForeignKeyConstraint(constraintName: 'FK_quota_2_resource_timetable',
                baseTableName: 'quota', baseTableSchemaName: 'jplanner', baseColumnNames: 'resource_timetable_id',
                referencedTableName: 'resource_timetable', referencedTableSchemaName: 'jplanner', referencedColumnNames: 'id')
    }

    changeSet(id: '2015-09-11-9', author: 'Vladimir Zburzhynski <zburzhynski@gmail.com>') {
        createTable(schemaName: 'jplanner', tableName: 'organization_timetable', tablespace: 'jplanner_data', remarks: 'Organization timetable') {
            column(name: 'id', type: 'VARCHAR(128)', remarks: 'The unique identifier of organization timetable') {
                constraints(nullable: false)
            }
            column(name: 'day_of_week', type: 'VARCHAR(10)', remarks: 'Day of week') {
                constraints(nullable: false)
            }
            column(name: 'start_time', type: 'TIMESTAMP', remarks: 'Start time of day') {
                constraints(nullable: false)
            }
            column(name: 'end_time', type: 'TIMESTAMP', remarks: 'End time of day') {
                constraints(nullable: false)
            }
            column(name: 'description', type: 'VARCHAR(500)', remarks: 'Description of timetable')
        }
        addPrimaryKey(schemaName: 'jplanner', tableName: 'organization_timetable', tablespace: 'jplanner_index',
                columnNames: 'id', constraintName: 'PK_organization_timetable')
    }

    changeSet(id: '2015-09-11-10', author: 'Vladimir Zburzhynski <zburzhynski@gmail.com>') {
        insert(schemaName: 'jplanner', tableName: 'organization_timetable') {
            column(name: 'id', value: '641864a6-227e-4911-86e1-9d2fbd2bebd3')
            column(name: 'day_of_week', value: 'MONDAY')
            column(name: 'start_time', value: '1970.01.01 08:00:00')
            column(name: 'end_time', value: '1970.01.01 20:00:00')
        }
        insert(schemaName: 'jplanner', tableName: 'organization_timetable') {
            column(name: 'id', value: '1deba19d-5b6d-453f-aff9-c645c912e1dc')
            column(name: 'day_of_week', value: 'TUESDAY')
            column(name: 'start_time', value: '1970.01.01 08:00:00')
            column(name: 'end_time', value: '1970.01.01 20:00:00')
        }
        insert(schemaName: 'jplanner', tableName: 'organization_timetable') {
            column(name: 'id', value: 'b5c66a7f-de85-4810-ac6e-44d7ef11a00a')
            column(name: 'day_of_week', value: 'WEDNESDAY')
            column(name: 'start_time', value: '1970.01.01 08:00:00')
            column(name: 'end_time', value: '1970.01.01 20:00:00')
        }
        insert(schemaName: 'jplanner', tableName: 'organization_timetable') {
            column(name: 'id', value: '913a9468-f45f-4c2d-aa0f-d2afb91908ce')
            column(name: 'day_of_week', value: 'THURSDAY')
            column(name: 'start_time', value: '1970.01.01 08:00:00')
            column(name: 'end_time', value: '1970.01.01 20:00:00')
        }
        insert(schemaName: 'jplanner', tableName: 'organization_timetable') {
            column(name: 'id', value: '1556865f-c256-45e6-bf24-c92da1962e42')
            column(name: 'day_of_week', value: 'FRIDAY')
            column(name: 'start_time', value: '1970.01.01 08:00:00')
            column(name: 'end_time', value: '1970.01.01 20:00:00')
        }
        insert(schemaName: 'jplanner', tableName: 'organization_timetable') {
            column(name: 'id', value: '71dc5578-f89d-48b3-a6c5-8c75dfeb8f1e')
            column(name: 'day_of_week', value: 'SATURDAY')
            column(name: 'start_time', value: '1970.01.01 08:00:00')
            column(name: 'end_time', value: '1970.01.01 20:00:00')
        }
        insert(schemaName: 'jplanner', tableName: 'organization_timetable') {
            column(name: 'id', value: '201ef102-3d73-466b-b14d-28b637b062ee')
            column(name: 'day_of_week', value: 'SUNDAY')
            column(name: 'start_time', value: '1970.01.01 08:00:00')
            column(name: 'end_time', value: '1970.01.01 20:00:00')
        }
    }

    changeSet(id: '2015-09-11-11', author: 'Vladimir Zburzhynski <zburzhynski@gmail.com>') {
        insert(schemaName: 'jplanner', tableName: 'setting') {
            column(name: 'id', value: '3e0c1587-fd3a-4453-afa8-c352bb02d245')
            column(name: 'category', value: 'VIEW')
            column(name: 'name', value: 'available_resources_per_page')
            column(name: 'value', value: '20')
            column(name: 'type', value: 'INTEGER')
            column(name: 'description', value: 'Количество доступных ресурсов на странице')
            column(name: 'sort_order', value: '5')
        }
    }

    changeSet(id: '2017-11-19-01', author: 'Nikita Shevtsov <shevtsou@gmail.com>') {
        comment("Added index to available_resource table assistant_id column")
        createIndex(schemaName: 'jplanner', tableName: 'available_resource', tablespace: 'jplanner_index', indexName: 'IDX_available_resource_assistant_id') {
            column(name: 'assistant_id')
        }
    }

    changeSet(id: '2017-11-19-02', author: 'Nikita Shevtsov <shevtsou@gmail.com>') {
        comment("Added index to available_resource table doctor_id column")
        createIndex(schemaName: 'jplanner', tableName: 'available_resource', tablespace: 'jplanner_index', indexName: 'IDX_available_resource_doctor_id') {
            column(name: 'doctor_id')
        }
    }

    changeSet(id: '2017-11-19-03', author: 'Nikita Shevtsov <shevtsou@gmail.com>') {
        comment("Added index to available_resource table workplace_id column")
        createIndex(schemaName: 'jplanner', tableName: 'available_resource', tablespace: 'jplanner_index', indexName: 'IDX_available_resource_workplace_id') {
            column(name: 'workplace_id')
        }
    }

    changeSet(id: '2017-11-19-04', author: 'Nikita Shevtsov <shevtsou@gmail.com>') {
        comment("Added index to client table person_id column")
        createIndex(schemaName: 'jplanner', tableName: 'client', tablespace: 'jplanner_index', indexName: 'IDX_client_person_id') {
            column(name: 'person_id')
        }
    }

    changeSet(id: '2017-11-19-05', author: 'Nikita Shevtsov <shevtsou@gmail.com>') {
        comment("Added index to employee table job_position_id column")
        createIndex(schemaName: 'jplanner', tableName: 'employee', tablespace: 'jplanner_index', indexName: 'IDX_employee_job_position_id') {
            column(name: 'job_position_id')
        }
    }

    changeSet(id: '2017-11-19-06', author: 'Nikita Shevtsov <shevtsou@gmail.com>') {
        comment("Added index to employee table person_id column")
        createIndex(schemaName: 'jplanner', tableName: 'employee', tablespace: 'jplanner_index', indexName: 'IDX_employee_person_id') {
            column(name: 'person_id')
        }
    }

    changeSet(id: '2017-11-19-07', author: 'Nikita Shevtsov <shevtsou@gmail.com>') {
        comment("Added index to quota table resource_timetable_id column")
        createIndex(schemaName: 'jplanner', tableName: 'quota', tablespace: 'jplanner_index', indexName: 'IDX_quota_resource_timetable_id') {
            column(name: 'resource_timetable_id')
        }
    }

    changeSet(id: '2017-11-19-08', author: 'Nikita Shevtsov <shevtsou@gmail.com>') {
        comment("Added index to resource_timetable table available_resource_id column")
        createIndex(schemaName: 'jplanner', tableName: 'resource_timetable', tablespace: 'jplanner_index', indexName: 'IDX_resource_timetable_available_resource_id') {
            column(name: 'available_resource_id')
        }
    }

    changeSet(id: '2017-11-19-09', author: 'Nikita Shevtsov <shevtsou@gmail.com>') {
        comment("Added index to schedule table client_id column")
        createIndex(schemaName: 'jplanner', tableName: 'schedule', tablespace: 'jplanner_index', indexName: 'IDX_schedule_client_id') {
            column(name: 'client_id')
        }
    }

    changeSet(id: '2017-11-19-10', author: 'Nikita Shevtsov <shevtsou@gmail.com>') {
        comment("Added index to schedule table doctor_id column")
        createIndex(schemaName: 'jplanner', tableName: 'schedule', tablespace: 'jplanner_index', indexName: 'IDX_schedule_doctor_id') {
            column(name: 'doctor_id')
        }
    }

    changeSet(id: '2017-11-19-11', author: 'Nikita Shevtsov <shevtsou@gmail.com>') {
        comment("Added index to schedule table workplace_id column")
        createIndex(schemaName: 'jplanner', tableName: 'schedule', tablespace: 'jplanner_index', indexName: 'IDX_schedule_workplace_id') {
            column(name: 'workplace_id')
        }
    }

    changeSet(id: '2017-11-19-12', author: 'Nikita Shevtsov <shevtsou@gmail.com>') {
        comment("Added index to workplace table cabinet_id column")
        createIndex(schemaName: 'jplanner', tableName: 'workplace', tablespace: 'jplanner_index', indexName: 'IDX_workplace_cabinet_id') {
            column(name: 'cabinet_id')
        }
    }

}