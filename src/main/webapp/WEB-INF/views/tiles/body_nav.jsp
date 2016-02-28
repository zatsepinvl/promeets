<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>

<!-- Page body-->
<main class="mdl-layout__content" style="background-color: #f5f5f5">
    <div class="page-content">

        <div class="mdl-grid">

            <!-- Navigation menu -->
            <div class="mdl-cell mdl-cell--2-col mdl-cell--8-col-tablet mdl-cell--4-col-phone">
                <tiles:insertAttribute name="nav"/>
            </div>

            <!-- Content container -->
            <div class="mdl-cell mdl-cell--8-col mdl-cell--8-col-tablet mdl-cell--4-col-phone">
                <tiles:insertAttribute name="content"/>
            </div>

            <!-- Recommendations -->
            <div class="mdl-cell mdl-cell--2-col mdl-cell--8-col-tablet mdl-cell--4-col-phone">
                <tiles:insertAttribute name="recom"/>
            </div>
        </div>
    </div>
</main>