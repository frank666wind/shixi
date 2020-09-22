$(function (){
    $.post({
        url:'user/getAll',
        success: function (data){
            console.log(data)
        }
    })
})