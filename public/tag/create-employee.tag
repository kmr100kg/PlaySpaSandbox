<create-employee>
    <div class="ui container default-margin-top">
        <h1>
            社員登録
        </h1>
        <div class="ui error message" show={errors} id="errorMsgBox">
            入力内容に誤りがあります。メッセージを確認して入力しなおしてください。
        </div>
        <div class="ui pointing menu">
            <a class="item active" data-tab="first">基本情報</a>
            <a class="item" data-tab="second">詳細情報</a>
            <a class="item" data-tab="third">所属情報</a>
        </div>
        <div class="ui bottom attached tab active" data-tab="first">
            <basic-user-info/>
        </div>
        <div class="ui bottom attached tab" data-tab="second">
            <user-detail-info/>
        </div>
        <div class="ui bottom attached tab" data-tab="third">
            <user-belong-info/>
        </div>
        <p/>
        <button class="ui button primary" onclick={create}>登録</button>
    </div>

    <script>
        const self = this

        self.on("mount", function () {

            $('.menu .item').tab();

            $('#department').dropdown({
                allowAdditions: true
            });

            $('#position').dropdown({
                allowAdditions: true
            });
        })

        create()
        {
            self.removeErrorClass()
            const userForm = {}
            $('input[name]').toArray().forEach(function (input) {
                const name = input.name
                userForm[name] = input.value
            })
            $.ajax({
                type: 'POST',
                url: '/create',
                contentType: 'application/json',
                data: JSON.stringify(userForm)
            }).done(function (data) {
                self.globalMessage.add('createdEmployee', data.successes)
                route('/list')
            }).fail(function (xhr) {
                self.dispMessage(self.globalErrorHandler.handle(xhr))
            })
        }

        dispMessage(msg)
        {
            self.errors = msg
            self.update()
            $('#errorMsgBox').transition({
                animation: 'fade in',
                duration: '2s'
            });
            for (key in self.errors) {
                $('#' + key + 'Field').addClass('error')
            }
        }

        removeErrorClass()
        {
            if (self.errors !== null && self.errors !== undefined) {
                for (key in self.errors) {
                    $('#' + key + 'Field').removeClass('error')
                }
            }
        }

    </script>
</create-employee>

