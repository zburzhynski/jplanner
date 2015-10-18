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
        createTable(schemaName: 'jplanner', tableName: 'timetable', tablespace: 'jplanner_data', remarks: 'Timetable') {
            column(name: 'id', type: 'VARCHAR(128)', remarks: 'The unique identifier of timetable') {
                constraints(nullable: false)
            }
            column(name: 'available_resource_id', type: 'VARCHAR(128)', remarks: 'The reference to the available resource table') {
                constraints(nullable: false)
            }
            column(name: 'start_date', type: 'TIMESTAMP', defaultValue: 'now()', remarks: 'Start date of timetable') {
                constraints(nullable: false)
            }
            column(name: 'end_date', type: 'TIMESTAMP', defaultValue: 'now()', remarks: 'End date of timetable') {
                constraints(nullable: false)
            }
            column(name: 'description', type: 'VARCHAR(500)', defaultValue: '', remarks: 'The timetable description')
        }
        addPrimaryKey(schemaName: 'jplanner', tableName: 'timetable', tablespace: 'jplanner_index',
                columnNames: 'id', constraintName: 'PK_timetable')
    }

    changeSet(id: '2015-09-11-6', author: 'Vladimir Zburzhynski <zburzhynski@gmail.com>') {
        addForeignKeyConstraint(constraintName: 'FK_timetable_2_available_resource',
                baseTableName: 'timetable', baseTableSchemaName: 'jplanner', baseColumnNames: 'available_resource_id',
                referencedTableName: 'available_resource', referencedTableSchemaName: 'jplanner', referencedColumnNames: 'id')
    }

    changeSet(id: '2015-09-11-7', author: 'Vladimir Zburzhynski <zburzhynski@gmail.com>') {
        createTable(schemaName: 'jplanner', tableName: 'quota', tablespace: 'jplanner_data', remarks: 'Time quota') {
            column(name: 'id', type: 'VARCHAR(128)', remarks: 'The unique identifier of quota') {
                constraints(nullable: false)
            }
            column(name: 'timetable_id', type: 'VARCHAR(128)', remarks: 'The reference to the timetable table') {
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
        addForeignKeyConstraint(constraintName: 'FK_quota_2_timetable',
                baseTableName: 'quota', baseTableSchemaName: 'jplanner', baseColumnNames: 'timetable_id',
                referencedTableName: 'timetable', referencedTableSchemaName: 'jplanner', referencedColumnNames: 'id')
    }

}