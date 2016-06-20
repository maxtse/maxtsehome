package com.max.tse.spring.template;

import org.apache.commons.lang.StringUtils;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.ui.velocity.VelocityEngineUtils;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: yuebin.xie
 * Date: 16-6-20
 * Time: 上午7:55
 * To change this template use File | Settings | File Templates.
 */

public class VelocityTemplateService {

    private VelocityEngine velocityEngine;

    private Map<String, String> templateMap;

    public VelocityTemplateService () {}

    public VelocityTemplateService(VelocityEngine velocityEngine, Map<String, String> templateMap) {
        this.velocityEngine = velocityEngine;
        this.templateMap = templateMap;
    }

    public VelocityEngine getVelocityEngine() {
        return velocityEngine;
    }

    public void setVelocityEngine(VelocityEngine velocityEngine) {
        this.velocityEngine = velocityEngine;
    }

    public Map<String, String> getTemplateMap() {
        return templateMap;
    }

    public void setTemplateMap(Map<String, String> templateMap) {
        this.templateMap = templateMap;
    }

    public String transform(String templateKey, Map<String, Object> params) {
        String templateName = this.templateMap.get(templateKey);
        if (StringUtils.isBlank(templateName)) {
            return "";
        }

        return VelocityEngineUtils.mergeTemplateIntoString(this.velocityEngine, templateName, params);


    }
}
