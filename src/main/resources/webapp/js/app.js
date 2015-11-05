var storage = angular.module('storage', []);

storage.controller('StorageCtrl', function ($log, $http, $scope) {

    var connect = function () {
        var socket = new WebSocket("ws://localhost:8406/websocket/storage");
        var stompClient = Stomp.over(socket);
        stompClient.connect({}, function (frame) {
            console.log('Connected: ' + frame);
            stompClient.subscribe('/topic/storage', function (response) {
                updateStateView(JSON.parse(response.body));
            });
        });
    };

    var updateElement = function(updateItem) {
        $log.info("update element:", updateItem);
        $scope.state.forEach(function(item) {
            if (item.name == updateItem.name) {
                $log.info("will update an existing item:", item);
                item.number = updateItem.number;
                $log.info("done");
            }
        });
        $scope.$digest()
    };

    var updateStateView = function (update) {
        if ($scope.setUp) {
            $log.info("state update: ", update);
            update.state.forEach(updateElement);
        }
    };

    var init = function() {
        $http.get('/rest/state').success(function(result) {
            $scope.state = result.state;
            $scope.setUp = true;
        });
    };

    $scope.state = {};

    connect();
    init();
});
