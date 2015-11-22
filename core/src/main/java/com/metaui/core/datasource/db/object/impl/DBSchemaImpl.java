package com.metaui.core.datasource.db.object.impl;

import com.metaui.core.datasource.db.object.*;
import com.metaui.core.datasource.db.object.enums.DBObjectType;
import com.metaui.core.util.UString;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author weijiancai
 * @version 1.0.0
 */
@XmlRootElement(name = "Schema")
public class DBSchemaImpl extends DBObjectImpl implements DBSchema {
    private List<DBTable> tables;
    private List<DBView> views;
    private List<DBIndex> indexes;
    private List<DBTrigger> triggers;
    private List<DBProcedure> procedures;
    private List<DBFunction> functions;
    private List<DBConstraint> constraints;
    private List<DBConstraint> fkConstraints;
    private Map<String, DBTable> tableMap = new HashMap<String, DBTable>();
    private Map<String, DBView> viewMap = new HashMap<String, DBView>();
    private Map<String, DBFunction> functionMap = new HashMap<String, DBFunction>();
    private Map<String, DBProcedure> procedureMap = new HashMap<String, DBProcedure>();
    private Map<String, DBConstraint> constraintMap = new HashMap<String, DBConstraint>();

    private DBLoader loader;

    public DBSchemaImpl() {
        setObjectType(DBObjectType.SCHEMA);
    }

    @Override
    public String getCatalog() {
        return null;
    }

    @Override
    @XmlElementWrapper(name = "Tables")
    @XmlAnyElement
    public List<DBTable> getTables() throws Exception {
        if (tables == null) {
            tables = loader.loadTables(this);
            // 放入map
            for (DBTable table : tables) {
                tableMap.put(table.getName().toLowerCase(), table);
            }
        }
        return tables;
    }

    @Override
    @XmlElementWrapper(name = "Views")
    @XmlAnyElement
    public List<DBView> getViews() throws Exception {
        if (views == null) {
            views = loader.loadViews(this);
            // 放入map
            for (DBView view : views) {
                viewMap.put(view.getName(), view);
            }
        }
        return views;
    }

    @Override
    public List<DBProcedure> getProcedures() {
        if (procedures == null) {
            procedures = loader.loadProcedures(this);
        }
        return procedures;
    }

    @Override
    public List<DBFunction> getFunctions() {
        if (functions == null) {
            functions = loader.loadFunctions(this);
        }
        return functions;
    }

    @Override
    public DBTable getTable(String name) throws Exception {
        if (UString.isEmpty(name)) {
            return null;
        }
        if (tables == null) {
            setTables(getTables());
        }
        return tableMap.get(name.toLowerCase());
    }

    @Override
    public DBView getView(String name) {
        return viewMap.get(name);
    }

    @Override
    public DBProcedure getProcedure(String name) {
        return procedureMap.get(name);
    }

    @Override
    public DBFunction getFunction(String name) {
        return functionMap.get(name);
    }

    @Override
    public DBConstraint getConstraint(String name) throws Exception {
        if (constraints == null) {
            setConstraints(getConstraints());
        }
        return constraintMap.get(name);
    }

    @Override
    public List<DBIndex> getIndexes() throws Exception {
        if (indexes == null) {
            indexes = loader.loadIndexes(this);
        }
        return indexes;
    }

    @Override
    public List<DBSynonym> getSynonyms() {
        return null;
    }

    @Override
    public List<DBSequence> getSequences() {
        return null;
    }

    @Override
    public List<DBPackage> getPackages() {
        return null;
    }

    @Override
    public List<DBTrigger> getTriggers() throws Exception {
        if (triggers == null) {
            triggers = loader.loadTriggers(this);
        }
        return triggers;
    }

    @Override
    public List<DBConstraint> getConstraints() throws Exception {
        if (constraints == null) {
            constraints = new ArrayList<DBConstraint>();
            for (DBTable table : getTables()) {
                constraints.addAll(loader.loadConstraint(table));
            }
        }
        return constraints;
    }

    @Override
    public List<DBConstraint> getFkConstraints() throws Exception {
        if (fkConstraints == null) {
            fkConstraints = loader.loadFkConstraints(this);
        }
        return fkConstraints;
    }

    public void setTables(List<DBTable> tables) {
        this.tables = tables;
        tableMap.clear();
        for (DBTable table : tables) {
            tableMap.put(table.getName().toLowerCase(), table);
        }
    }

    @Override
    public DBSchema getSchema() {
        return this;
    }

    public void setViews(List<DBView> views) {
        this.views = views;
    }

    public void setIndexes(List<DBIndex> indexes) {
        this.indexes = indexes;
    }

    public void setTriggers(List<DBTrigger> triggers) {
        this.triggers = triggers;
    }

    public void setProcedures(List<DBProcedure> procedures) {
        this.procedures = procedures;
        procedureMap.clear();
        for (DBProcedure procedure : procedures) {
            procedureMap.put(procedure.getName(), procedure);
        }
    }

    public void setFunctions(List<DBFunction> functions) {
        this.functions = functions;
        functionMap.clear();
        for (DBFunction function : functions) {
            functionMap.put(function.getName(), function);
        }
    }

    public void setConstraints(List<DBConstraint> constraints) {
        this.constraints = constraints;
        constraintMap.clear();
        for (DBConstraint constraint : constraints) {
            constraintMap.put(constraint.getName(), constraint);
        }
    }

    public void addConstraints(DBConstraint constraint) throws Exception {
        getConstraints().add(constraint);
        constraintMap.put(constraint.getName(), constraint);
    }

    public DBLoader getLoader() {
        return loader;
    }

    public void setLoader(DBLoader loader) {
        this.loader = loader;
    }

    @Override
    public String getFullName() {
        return "[" + getDataSource().getName() + "] " + getName();
    }
}
