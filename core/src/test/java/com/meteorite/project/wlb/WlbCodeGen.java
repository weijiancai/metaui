package com.meteorite.project.wlb;

import com.metaui.core.codegen.CodeGen;
import com.metaui.core.config.SystemManager;
import com.metaui.core.meta.MetaManager;
import com.metaui.core.project.ProjectDefine;
import com.metaui.core.project.ProjectManager;

/**
 * @author wei_jc
 * @since 1.0.0
 */
public class WlbCodeGen {
    public static void main(String[] args) throws Exception {
        SystemManager.getInstance().init();

        ProjectDefine project = ProjectManager.getProjectByName("XinJu");
        CodeGen codeGen = new CodeGen(project, MetaManager.getMeta("News"));
        codeGen.gen();
    }
}
