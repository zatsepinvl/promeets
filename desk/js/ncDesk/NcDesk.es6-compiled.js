'use strict';

var _createClass = function () {
    function defineProperties(target, props) {
        for (var i = 0; i < props.length; i++) {
            var descriptor = props[i];
            descriptor.enumerable = descriptor.enumerable || false;
            descriptor.configurable = true;
            if ("value" in descriptor) descriptor.writable = true;
            Object.defineProperty(target, descriptor.key, descriptor);
        }
    }

    return function (Constructor, protoProps, staticProps) {
        if (protoProps) defineProperties(Constructor.prototype, protoProps);
        if (staticProps) defineProperties(Constructor, staticProps);
        return Constructor;
    };
}();

function _possibleConstructorReturn(self, call) {
    if (!self) {
        throw new ReferenceError("this hasn't been initialised - super() hasn't been called");
    }
    return call && (typeof call === "object" || typeof call === "function") ? call : self;
}

function _inherits(subClass, superClass) {
    if (typeof superClass !== "function" && superClass !== null) {
        throw new TypeError("Super expression must either be null or a function, not " + typeof superClass);
    }
    subClass.prototype = Object.create(superClass && superClass.prototype, {
        constructor: {
            value: subClass,
            enumerable: false,
            writable: true,
            configurable: true
        }
    });
    if (superClass) Object.setPrototypeOf ? Object.setPrototypeOf(subClass, superClass) : subClass.__proto__ = superClass;
}

function _classCallCheck(instance, Constructor) {
    if (!(instance instanceof Constructor)) {
        throw new TypeError("Cannot call a class as a function");
    }
}

/**
 * Created by Filon_000 on 3/1/2016.
 */

var NcDesk = function () {
    function NcDesk($wrapper) {
        _classCallCheck(this, NcDesk);

        this.$wrapper = $wrapper;
        this.stickers = [];
        this.initEventListeners();
        this.color = "green";
        switch (this.color) {
            case 'green':
                this.secondaryColor = 'white';
                break;
            default:
                this.secondaryColor = "blue";
                throw new Error('Unknown color used');
                break;
        }
    }

    _createClass(NcDesk, [{
        key: 'addNewSticker',
        value: function addNewSticker(type) {
            switch (type) {
                case "text":
                    this.addSticker(new TextSticker(this.color));
                    break;
                default:
                    throw new Error("Not such sticker type : " + type);
                    break;
            }
        }
    }, {
        key: 'addSticker',
        value: function addSticker(sticker) {
            if (!sticker instanceof Sticker) {
                throw new Error('Sticker is not instance of class Sticker');
            }
            this.stickers.push(sticker);
            var $sticker = $(sticker.html);
            this.$wrapper.find(".nc-desk-control").append($sticker);
            sticker.initSticker($sticker);
        }
    }, {
        key: 'initEventListeners',
        value: function initEventListeners() {
            var _this = this;

            this.$wrapper.find(".sticker-type-select .text-sticker").click(function () {
                _this.addNewSticker('text');
            });
        }
    }]);

    return NcDesk;
}();

var lastId = 0;

var Sticker = function () {
    function Sticker(color) {
        _classCallCheck(this, Sticker);

        this.color = color;
    }

    _createClass(Sticker, [{
        key: 'initSticker',
        value: function initSticker($sticker) {
            var _this2 = this;

            this.$sticker = $sticker;
            var addToDeskCallback = function addToDeskCallback() {
                _this2.addToDeskHandler($sticker);
            };
            $sticker.on('click', '.add-to-desk', addToDeskCallback);
        }
        //override

    }, {
        key: 'transformToDeskWidget',
        value: function transformToDeskWidget() {
        }
    }, {
        key: 'transformToSubmittedDeskWidget',
        value: function transformToSubmittedDeskWidget() {
        }
    }, {
        key: 'submitToDeskHandler',
        value: function submitToDeskHandler($sticker) {
            this.transformToSubmittedDeskWidget($sticker);
        }
    }, {
        key: 'addToDeskHandler',
        value: function addToDeskHandler($sticker) {
            var $wrapper = $sticker.wrap('<div class="sticker-animation-wrapper"></div>');
            var me = this;
            $sticker.animate({
                bottom: 100,
                opacity: 0
            }, 'fast').promise().done(function () {
                $sticker.remove();
                $wrapper.remove();
                $sticker = me.transformToDeskWidget($sticker);
                me.$sticker = $sticker;
                $sticker.appendTo($(".nc-desk-canvas")).css("bottom", -100).animate({
                    bottom: 0,
                    opacity: 1
                }, 'fast');
            });
        }
    }], [{
        key: 'getNewId',
        value: function getNewId() {
            return lastId++;
        }
    }]);

    return Sticker;
}();

var TextSticker = function (_Sticker) {
    _inherits(TextSticker, _Sticker);

    function TextSticker(color) {
        _classCallCheck(this, TextSticker);

        var _this3 = _possibleConstructorReturn(this, Object.getPrototypeOf(TextSticker).call(this, color));

        _this3.id = Sticker.getNewId();
        _this3._html = _this3.getSnippet();
        return _this3;
    }

    _createClass(TextSticker, [{
        key: 'getSnippet',
        value: function getSnippet() {
            return '\n            <div class="nc-text-sticker nc-sticker mdl-card mdl-shadow--2dp" id="textsticker-' + this.id + '" data-id="' + this.id + '">\n                <div class="mdl-card__title mdl-card--expand">\n                    <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">\n                        <textarea class="mdl-textfield__input" rows="3" type="text" id="textsticker-' + this.id + '"></textarea>\n                        <label class="mdl-textfield__label" for="textsticker-' + this.id + '">Sticker</label>\n                    </div>\n                </div>\n                <div class="mdl-card__actions mdl-card--border">\n                    <a class="add-to-desk mdl-button mdl-button--colored mdl-js-button mdl-js-ripple-effect">Add to desk </a>\n                    <a class="mdl-button mdl-button--colored mdl-js-button mdl-js-ripple-effect">\n                        <i class="material-icons">delete</i>\n                    </a>\n                </div>\n            </div>';
        }
    }, {
        key: 'transformToDeskWidget',
        value: function transformToDeskWidget($sticker) {
            var _this4 = this;

            $sticker.draggable({
                containment: ".nc-desk-canvas", scroll: false
            });
            $sticker.find(".mdl-card__actions .add-to-desk").replaceWith('<a class="submit-to-desk mdl-button mdl-button--colored mdl-js-button mdl-js-ripple-effect">Submit to desk </a>');
            $sticker.css('background-color', this.color);

            var submitToDeskCallback = function submitToDeskCallback() {
                _this4.submitToDeskHandler($sticker);
            };
            $sticker.on('click', '.submit-to-desk', submitToDeskCallback);

            return $sticker;
        }
    }, {
        key: 'transformToSubmittedDeskWidget',
        value: function transformToSubmittedDeskWidget($sticker) {
            $sticker.draggable('disable');
            $sticker.find(".mdl-card__actions .submit-to-desk").remove();
            this.text = $sticker.find(".mdl-textfield textarea").val();
            $sticker.find(".mdl-textfield").replaceWith('<div class="widget-text">' + this.text + '</div>');
            return $sticker;
        }
    }, {
        key: 'html',
        get: function get() {
            return this._html;
        }
    }]);

    return TextSticker;
}(Sticker);

//# sourceMappingURL=NcDesk.es6-compiled.js.map