<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://www.springframework.org/tags" %>



<html>
      <div ng-app = "app" ng-controller = "chatController">
		<h2> {{currentUser.firstName}} {{currentUser.lastName}}: </h2>
		<form ng-submit="sendMessage()">
			<input type="text" ng-model="messageText"  size="30"
				   placeholder="message..">
			<input class="btn-primary" type="submit" value="добавить">
		</form>
		<p ng-repeat="message in messages | orderBy:'time':true" class="message">
			<time>{{message.time | date:'HH:mm'}}</time>
			<span ng-class="{self: message.self}">{{message.user.firstName}} {{message.user.lastName}}:{{message.text}}</span>
		</p>
      </div>
</html>