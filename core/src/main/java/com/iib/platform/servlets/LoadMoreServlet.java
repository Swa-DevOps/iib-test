package com.iib.platform.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.json.JSONArray;
import org.json.JSONObject;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.wcm.api.Page;
import com.iib.platform.common.utils.LinkUtil;

/**
 * Load More Servlet
 *
 * @author TechChefz (TCZ Consulting LLP)
 *
 */

@Component(service = { Servlet.class }, property = { "sling.servlet.methods=" + HttpConstants.METHOD_POST,
		"sling.servlet.paths=" + "/bin/loadMore" })
public class LoadMoreServlet extends SlingAllMethodsServlet {

	private static final long serialVersionUID = 1L;

	/* Logger */
	private static Logger log = LoggerFactory.getLogger(LoadMoreServlet.class);

	@Override
	protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response)
			throws ServletException, IOException {
		SimpleDateFormat formatter;
		PrintWriter out = response.getWriter();
		String articlesMode = request.getParameter("articlesListingMode");
		String parentPagePath = request.getParameter("parentPagePath");

		if (articlesMode.equalsIgnoreCase("auto")) {
			try {
				ResourceResolver resourceResolver;
				JSONArray arr = new JSONArray();

				formatter = new SimpleDateFormat("dd MMMM yyyy");
				String pagePath = parentPagePath;

				if (pagePath != null) {
					resourceResolver = request.getResourceResolver();
					Resource pageResource = resourceResolver.getResource(pagePath);
					if ((pageResource != null) && (pageResource instanceof Resource)) {
						Iterable<Resource> pageChildren = pageResource.getChildren();
						for (Resource eachPage : pageChildren) {
							if (!eachPage.getName().equalsIgnoreCase("jcr:content")) {
								Page page = eachPage.adaptTo(Page.class);
								if (page != null) {
									ValueMap pageProperties = page.getProperties();
									String thumbnailImagePath = pageProperties.get("thumbnailImagePath", String.class);
									String title = pageProperties.get("jcr:title", String.class);
									Calendar calDate = pageProperties.get("jcr:created", Calendar.class);
									String tagImagePath = pageProperties.get("tagImagePath", String.class);
									String formattedDate = "";
									if (calDate != null)
										formattedDate = formatter.format(calDate.getTime());
									String pageUrl = LinkUtil.getFormattedURL(page.getPath());
									JSONObject jsonObj = new JSONObject();
									jsonObj.put("thumbnailImagePath", thumbnailImagePath);
									jsonObj.put("title", title);
									jsonObj.put("formattedDate", formattedDate);
									jsonObj.put("tagImagePath", tagImagePath);
									jsonObj.put("pageUrl", resourceResolver.map(request, pageUrl));
									arr.put(jsonObj);
								}
							}
						}
						out.println(arr.toString());
					}
				}
			} catch (Exception e) {
				log.error("Exception in LoadMoreServlet :: ", e);
			}
		}
	}
}