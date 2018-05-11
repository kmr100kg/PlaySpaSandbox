var globalErrorHandler = {
    handle: function(xhr) {
        if (xhr.status === 400) {
            return JSON.parse(xhr.responseText)["errors"]
        } else if (xhr.status === 403) {
            return JSON.parse(xhr.responseText)["errors"]
        } else {
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