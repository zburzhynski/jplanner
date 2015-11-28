databaseChangeLog {

    changeSet(id: '2015-09-11-1', author: 'Vladimir Zburzhynski <zburzhynski@gmail.com>') {
        createTable(schemaName: 'jplanner', tableName: 'available_resource', tablespace: 'jplanner_data', remarks: 'Available resource') {
            column(name: 'id', type: 'VARCHAR(128)', remarks: 'The unique identifier of available resource') {
                constraints(nullable: false)
            }
            column(name: 'name', type: 'VARCHAR(100)', defaultValue: '', remarks: 'The available resource name') {
                constraints(nullable: false)
            }
            column(name: 'doctor_id', type: 'VARCHAR(128)', remarks: 'The reference to the employee table') {
                constraints(nullable: false)
            }
            column(name: 'assistant_id', type: 'VARCHAR(128)', remarks: 'The reference to the employee table')
            column(name: 'workplace_id', type: 'VARCHAR(128)', remarks: 'The reference to the workplace table') {
                constraints(nullable: false)
            }
            column(name: 'description', type: 'VARCHAR(500)', defaultValue: '', remarks: 'The available resource description')
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
            column(name: 'start_date', type: 'TIMESTAMP', defaultValue: 'now()', remarks: 'Start date of resource timetable') {
                constraints(nullable: false)
            }
            column(name: 'end_date', type: 'TIMESTAMP', defaultValue: 'now()', remarks: 'End date of resource timetable') {
                constraints(nullable: false)
            }
            column(name: 'description', type: 'VARCHAR(500)', defaultValue: '', remarks: 'The resource timetable description')
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
            column(name: 'start_date', type: 'TIMESTAMP', defaultValue: 'now()', remarks: 'Start date of quota') {
                constraints(nullable: false)
            }
            column(name: 'end_date', type: 'TIMESTAMP', defaultValue: 'now()', remarks: 'End date of quota') {
                constraints(nullable: false)
            }
            column(name: 'quota_type', type: 'VARCHAR(15)', defaultValue: '', remarks: 'The quota type') {
                constraints(nullable: false)
            }
            column(name: 'description', type: 'VARCHAR(500)', defaultValue: '', remarks: 'The quota description')
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
            column(name: 'day_of_week', type: 'VARCHAR(10)', defaultValue: '', remarks: 'Day of week') {
                constraints(nullable: false)
            }
            column(name: 'start_time', type: 'TIMESTAMP', defaultValue: '1970.01.01 00:00:00', remarks: 'Start time of day') {
                constraints(nullable: false)
            }
            column(name: 'end_time', type: 'TIMESTAMP', defaultValue: '1970.01.01 00:00:00', remarks: 'End time of day') {
                constraints(nullable: false)
            }
            column(name: 'description', type: 'VARCHAR(500)', defaultValue: '', remarks: 'Description of timetable')
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

}