app.controller("cardsCtrl", function ($scope,
                                      appConst,
                                      Entity,
                                      $state,
                                      UserService,
                                      MeetService,
                                      CardEditDialogService,
                                      ConfirmDialog,
                                      EventHandler) {
    $scope.cards = MeetService.getCards();

    $scope.createCard = function () {
        var card = {
            user: $scope.user,
            meet: $scope.meet,
            time: moment().utc().valueOf(),
            isNew: true
        };

        Entity.save({entity: "cards"}, card,
            function (data) {
                clone(data, card);
                $scope.newCard = card;
                $scope.editingCard = card;
            }
        );
    };

    $scope.editCard = function (card) {
        card.editing = true;
        $scope.editingCard = card;
    };

    $scope.saveCard = function (card) {
        card.editing = false;
        if (card.isNew) {
            $scope.cards.push(card);
            card.isNew = false;
            $scope.newCard = undefined;
        }
        $scope.editingCard = undefined;
        Entity.update({entity: "cards", id: card.cardId}, card);
    };

    $scope.deleteCard = function (card, event) {
        ConfirmDialog.show('Delete card?', 'Delete', 'Cancel', event,
            function () {
                //EventHandler.load('Deleting card');
                Entity.remove({entity: "cards", id: card.cardId}, function () {
                        if (!card.isNew) {
                            EventHandler.message('Card has been deleted');
                            $scope.cards.splice($scope.cards.indexOf(card), 1);
                        }
                        $scope.newCard = undefined;
                    },
                    function (error) {
                        if (!card.isNew) {
                            EventHandler.message('Something went wrong. Try again later.');
                        }
                        $scope.newCard = undefined;
                    });
            });
    };

    $scope.onCardFileUploaded = function (file) {
        var card = $scope.editingCard;
        Entity.save({entity: "cards", id: card.cardId, d_entity: "files"},
            function (newFile) {
                card.files.push(newFile);
            })
    };

    $scope.onCardFileDelete = function (file) {
        var card = $scope.editingCard;
        Entity.remove({entity: "cards", id: card.cardId, d_entity: "files", d_id: file.fileId},
            function () {
                card.files.splice(card.files.indexOf(file), 1);
            })
    };

    $scope.onCardImageDelete = function (file) {
        file.url = null;
        file.name = null;
        Entity.update({entity: "files", id: file.fileId}, file);
    };

    $scope.$on('card', function (event, message) {
        if (message.data.meet.meetId === $scope.meet.meetId) {
            if (message.action == appConst.ACTION.UPDATE) {
                var newCard = true;
                for (var i = 0; i < $scope.cards.length; i++) {
                    if ($scope.cards[i].cardId == message.data.cardId) {
                        newCard = false;
                        $scope.cards[i] = message.data;
                        break;
                    }
                }
                if (newCard) {
                    $scope.cards.push(message.data);
                    EventHandler.setNew(message.data);
                    var user = message.data.user;
                    EventHandler.message('New card by ' + user.firstName + ' ' + user.lastName, user.image.url);
                }

                $scope.$apply();
            }
            else if (message.action == appConst.ACTION.DELETE) {
                $scope.cards.splice($scope.cards.indexOf(message.data), 1);
                $scope.$apply();
            }
        }
    });
});