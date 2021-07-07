package com.example.topoftops.tag;

import java.io.IOException;
import java.io.StringWriter;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import javax.servlet.jsp.tagext.TagSupport;

import com.example.topoftops.entity.Item;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ItemCustomTag extends TagSupport {
    private static final Logger logger = LogManager.getLogger();
    private static final String P_TAG_START = "<p>";
    private static final String P_TAG_END = "</p>";
    private static final String H_TAG_START = "<h1>";
    private static final String H_TAG_END = "</h1>";
    private Item item;

    public void setItem(Item item) {
        this.item = item;
    }

    @Override
    public int doStartTag() throws JspTagException {
        try {
            JspWriter out = pageContext.getOut();
            out.write(H_TAG_START);
            out.write(String.valueOf(item.getPlace()));
            out.write(H_TAG_END);
            out.write(P_TAG_START);
            out.write(item.getTitle());
            out.write(P_TAG_END);
            out.write(P_TAG_START);
            out.write( item.getDescription());
            out.write(P_TAG_END);

        } catch (IOException e) {
            logger.error(" I/O error occurs", e);
            throw new JspTagException(e);
        }
        return SKIP_BODY;
    }

    @Override
    public int doEndTag() throws JspTagException {
        try {
            JspWriter out = pageContext.getOut();
            out.write("");
        } catch (IOException e) {
            logger.error(" I/O error occurs", e);
            throw new JspTagException(e);
        }
        return EVAL_PAGE;
    }
}
