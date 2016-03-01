/**
 * Created by Filon_000 on 3/1/2016.
 */
class NcDesk {
    constructor($wrapper) {
        this.$wrapper = $wrapper;
        this.stickers = [];
        this.initEventListeners();
        this.color = "green";
        switch (this.color) {
            case 'green' :
                this.secondaryColor = 'white';
                break;
            default :
                this.secondaryColor = "blue";
                throw new Error('Unknown color used');
                break;
        }
    }

    addNewSticker(type) {
        switch (type) {
            case "text":
                this.addSticker(new TextSticker(this.color));
                break;
            default:
                throw new Error("Not such sticker type : " + type);
                break;
        }

    }

    addSticker(sticker) {
        if (!sticker instanceof Sticker) {
            throw new Error('Sticker is not instance of class Sticker');
        }
        this.stickers.push(sticker);
        let $sticker = $(sticker.html);
        this.$wrapper.find(".nc-desk-control").append($sticker);
        sticker.initSticker($sticker);
    }

    initEventListeners() {
        this.$wrapper.find(".sticker-type-select .text-sticker").click(()=> {
                this.addNewSticker('text');
            }
        );
    }

}

let lastId = 0;
class Sticker {
    constructor(color) {
        this.color = color;
    }

    static getNewId() {
        return lastId++;
    }

    initSticker($sticker) {
        this.$sticker = $sticker;
        let addToDeskCallback = () => {
            this.addToDeskHandler($sticker);
        };
        $sticker.on('click', '.add-to-desk', addToDeskCallback);
    }

    //override
    transformToDeskWidget() {
    }

    transformToSubmittedDeskWidget() {
    }

    submitToDeskHandler($sticker) {
        this.transformToSubmittedDeskWidget($sticker);
    }

    addToDeskHandler($sticker) {
        let $wrapper = $sticker.wrap('<div class="sticker-animation-wrapper"></div>');
        let me = this;
        $sticker.animate({
            bottom: 100,
            opacity: 0
        }, 'fast').promise().done(()=> {
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
}
class TextSticker extends Sticker {

    constructor(color) {
        super(color);
        this.id = Sticker.getNewId();
        this._html = this.getSnippet();
    }

    getSnippet() {
        return `
            <div class="nc-text-sticker nc-sticker mdl-card mdl-shadow--2dp" id="textsticker-${this.id}" data-id="${this.id}">
                <div class="mdl-card__title mdl-card--expand">
                    <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
                        <textarea class="mdl-textfield__input" rows="3" type="text" id="textsticker-${this.id}"></textarea>
                        <label class="mdl-textfield__label" for="textsticker-${this.id}">Sticker</label>
                    </div>
                </div>
                <div class="mdl-card__actions mdl-card--border">
                    <a class="add-to-desk mdl-button mdl-button--colored mdl-js-button mdl-js-ripple-effect">Add to desk </a>
                    <a class="mdl-button mdl-button--colored mdl-js-button mdl-js-ripple-effect">
                        <i class="material-icons">delete</i>
                    </a>
                </div>
            </div>`;

    }

    transformToDeskWidget($sticker) {
        $sticker.draggable({
            containment: ".nc-desk-canvas", scroll: false
        });
        $sticker.find(".mdl-card__actions .add-to-desk").replaceWith('<a class="submit-to-desk mdl-button mdl-button--colored mdl-js-button mdl-js-ripple-effect">Submit to desk </a>');
        $sticker.css('background-color', this.color);

        let submitToDeskCallback = () => {
            this.submitToDeskHandler($sticker);

        };
        $sticker.on('click', '.submit-to-desk', submitToDeskCallback);

        return $sticker;
    }

    transformToSubmittedDeskWidget($sticker) {
        $sticker.draggable('disable');
        $sticker.find(".mdl-card__actions .submit-to-desk").remove();
        this.text = $sticker.find(".mdl-textfield textarea").val();
        $sticker.find(".mdl-textfield").replaceWith('<div class="widget-text">' + this.text + '</div>');
        return $sticker;
    }

    get html() {
        return this._html;
    }
}