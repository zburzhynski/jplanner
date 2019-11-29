databaseChangeLog {

    changeSet(id: '2019-11-09-01', author: 'Vladimir Zburzhynski <zburzhynski@gmail.com>') {
        comment("Added color column to employee table")
        addColumn(schemaName: 'jplanner', tableName: 'employee') {
            column(name: 'color', type: 'VARCHAR(10)', defaultValue: 'f7fcfc', remarks: "Employee color") {
                constraints(nullable: false)
            }
        }
    }

    changeSet(id: '2019-11-09-02', author: 'Vladimir Zburzhynski <zburzhynski@gmail.com>') {
        comment("Added description column to schedule table")
        addColumn(schemaName: 'jplanner', tableName: 'schedule') {
            column(name: 'description', type: 'VARCHAR(1100)', remarks: "Schedule description")
        }
    }

}