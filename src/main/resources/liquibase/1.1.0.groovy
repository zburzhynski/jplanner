databaseChangeLog {

    changeSet(id: '2015-08-20-1', author: 'Vladimir Zburzhynski <zburzhynski@gmail.com>') {
        createTable(schemaName: 'jplanner', tableName: 'timetable', tablespace: 'jplanner_data', remarks: 'Timetable') {
            column(name: 'id', type: 'VARCHAR(128)', remarks: 'The unique identifier of timetable') {
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

    changeSet(id: '2015-08-20-2', author: 'Vladimir Zburzhynski <zburzhynski@gmail.com>') {
        createTable(schemaName: 'jplanner', tableName: 'quota', tablespace: 'jplanner_data', remarks: 'Time quota') {
            column(name: 'id', type: 'VARCHAR(128)', remarks: 'The unique identifier of quota') {
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

    changeSet(id: '2015-08-20-3', author: 'Vladimir Zburzhynski <zburzhynski@gmail.com>') {
        createTable(schemaName: 'jplanner', tableName: 'timetable_quota', tablespace: 'jplanner_data', remarks: 'Time timetable quota mapping') {
            column(name: 'timetable_id', type: 'VARCHAR(128)', remarks: 'The reference to the timetable table') {
                constraints(nullable: false)
            }
            column(name: 'quota_id', type: 'VARCHAR(128)', remarks: 'The reference to the quota table') {
                constraints(nullable: false)
            }
        }
        addPrimaryKey(schemaName: 'jplanner', tableName: 'timetable_quota', tablespace: 'jplanner_index',
                columnNames: 'timetable_id, quota_id', constraintName: 'PK_timetable_quota')
    }

    changeSet(id: '2015-08-20-4', author: 'Vladimir Zburzhynski <zburzhynski@gmail.com>') {
        createTable(schemaName: 'jplanner', tableName: 'employee_timetable', tablespace: 'jplanner_data', remarks: 'Employee timetable mapping') {
            column(name: 'employee_id', type: 'VARCHAR(128)', remarks: 'The reference to the employee table') {
                constraints(nullable: false)
            }
            column(name: 'timetable_id', type: 'VARCHAR(128)', remarks: 'The reference to the timetable table') {
                constraints(nullable: false)
            }
        }
        addPrimaryKey(schemaName: 'jplanner', tableName: 'employee_timetable', tablespace: 'jplanner_index',
                columnNames: 'employee_id, timetable_id', constraintName: 'PK_employee_timetable')
    }

}