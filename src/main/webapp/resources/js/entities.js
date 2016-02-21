/*модели данных*/
/**
 * Created by 803581 on 21.02.2016.
 */
function User(_userId, _firstName){
    this.userId = _userId;
    this.firstName = _firstName;
    this.img = "../image/user_image.png";
    /*other properties<...>*/
}
function File(_fieldId, _url){
    this.fieldId = _fieldId;
    this.url = _url;
}
function Chat(_chatId, _name){
    this.chatId = _chatId;
    this.name = _name;
    this.users = [];
    this.messages = [];
}
function GroupType(_typeId, _name){
    this.typeId = _typeId;
    this.name = _name;
}
Chat.prototype.addUserToChat= function(user){
    this.users.push(user);
};

function Group(_groupId){
    this.groupId = _groupId;
    this.name = "";
    this.status = "";
    this.createdTime = new Date(1970,0,0);
    this.chat = null;
    this.image = null;
    this.type = null;
    this.groupType = null;
    this.admin = null;
}
Group.prototype.users = [];
/*where _user typeof User*/
Group.prototype.addUser = function(_user){
    this.users.push(_user);
};
/*_chat typeof Chat*/
Group.prototype.setChat = function(_chat){
    this.chat   = _chat;
};
/*_img typeof File*/
Group.prototype.setImage = function(_img){
    this.image   = _img;
};
/*_type typeof Type*/
Group.prototype.setType = function(_type){
    this.type   = _type;
};
Group.prototype.setAdmin = function(_admin){
    this.admin   = _admin;
};


