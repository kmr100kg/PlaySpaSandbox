var globalErrorHandler = {
    handle: function(xhr, doSomethingBeforeJump) {
        if (doSomethingBeforeJump === null || doSomethingBeforeJump === undefined) {
            doSomethingBeforeJump = function () {}
        }

        if (xhr.status === 400) {
            return JSON.parse(xhr.responseText)["errors"]
        } else if (xhr.status === 403) {
            doSomethingBeforeJump()
            route('/forbidden')
            return {}
        } else if (xhr.status === 404) {
            doSomethingBeforeJump()
            route('/notFound')
            return {}
        } else {
            doSomethingBeforeJump()
            route('/error')
            return {}
        }
    }
}

var globalMessage = {
    messages: {},
    take: function (key) {
        const msg = this.messages[key]
        delete this.messages[key]
        return msg
    },
    add: function (key, msg) {
        this.messages[key] = msg
        return msg
    }
}

function fadeMessage(id)
{
    $('#' + id).transition({
        animation: 'fade in',
        duration: '2s'
    });
}
