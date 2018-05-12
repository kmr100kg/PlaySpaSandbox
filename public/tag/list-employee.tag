<list-employee>
    <div class="ui container default-margin-top">
        <h1>
            社員一覧
        </h1>
        <div class="ui success message" id="successMsgBox" show={successes}>
            <i class="close icon"></i>
            <div class="header">
                Completed
            </div>
            <p>{successes}</p>
        </div>
        <div class="ui success message" id="errorMsgBox" show={errors}>
            <i class="close icon"></i>
            <div class="header">
                Failed
            </div>
            <p>{errors}</p>
        </div>
        <table class="ui very basic table" id="employeeTable">
            <tr>
                <th>社員番号</th>
                <th>氏名</th>
                <th>カナ</th>
                <th>メールアドレス</th>
                <th>パスワード</th>
                <th>&nbsp;</th>
            </tr>
            <tr each={emp, i in employees} class="emp-row-{i}">
                <td>{ emp.employeeNumber }</td>
                <td>{ emp.name }</td>
                <td>{ emp.kana }</td>
                <td>{ emp.mailAddress }</td>
                <td>{ emp.password }</td>
                <td class="right aligned">
                    <div class="tiny ui green button" emp-index={i} onclick={popUpEditForm}>編集</div>
                    <div class="tiny ui red button" emp-no={emp.employeeNumber} emp-name={emp.name} onclick={delConfirm}>削除</div>
                </td>
            </tr>
        </table>
    </div>

    <div class="ui tiny modal" id="deleteModal">
        <div class="header">削除</div>
        <div class="content">
            <p>以下の社員を削除しますか？</p>
            <table class="ui very basic table">
                <tr>
                    <th>社員番号</th>
                    <th>氏名</th>
                </tr>
                <tr>
                    <td>{empNo}</td>
                    <td>{empName}</td>
                </tr>
            </table>
        </div>
        <div class="actions">
            <div class="ui negative button">いいえ</div>
            <div class="ui positive right labeled icon button" emp-no={empNo} onclick={del}>
                はい
                <i class="checkmark icon"></i>
            </div>
        </div>
    </div>

    <div class="ui modal" id="editModal">
        <div class="header">編集</div>
        <div class="content">
            <div class="ui form" id="editModalForm">
                <div class="two fields">
                    <div class="field">
                        <label>社員番号(変更不可)</label>
                        <input type="text" value={getEditEmp('employeeNumber')} readonly="">
                    </div>
                    <div class="field">
                        <label>氏名<span class="required-input">*</span></label>
                        <input type="text" value={getEditEmp('name')}>
                    </div>
                </div>
                <div class="two fields">
                    <div class="field">
                        <label>カナ(全角カナ)<span class="required-input">*</span></label>
                        <input type="text" value={getEditEmp('kana')}>
                    </div>
                    <div class="field">
                        <label>メールアドレス<span class="required-input">*</span></label>
                        <input type="text" value={getEditEmp('mailAddress')}>
                    </div>
                </div>
                <div class="two fields">
                    <div class="field">
                        <label>パスワード(8桁以上)<span class="required-input">*</span></label>
                        <input type="password" value={getEditEmp('password')}>
                    </div>
                    <div class="field">
                        <label>パスワード確認(8桁以上)<span class="required-input">*</span></label>
                        <input type="password" value={getEditEmp('password')}>
                    </div>
                </div>
            </div>
        </div>
        <div class="actions">
            <div class="ui negative button">キャンセル</div>
            <div class="ui positive right labeled icon button" onclick={prepareEdit}>
                更新
                <i class="checkmark icon"></i>
            </div>
        </div>
    </div>

    <script>
        const self = this

        self.on("mount", function () {
            self.list()
            self.dispGlobalMessage()
        })

        list()
        {
            $.ajax({
                type: 'GET',
                url: '/list',
                contentType: 'application/json',
                dataType: 'json'
            }).done(function (data) {
                self.employees = data
                self.update()
            }).fail(function (xhr) {
                self.dispMessage('fail', self.globalErrorHandler.handle(xhr))
            })
        }

        popUpEditForm(e)
        {
            const empIndex = e.currentTarget.getAttribute('emp-index')
            $('.emp-row-' + empIndex).each(function () {
                var emp = $(this).children()
                self.editEmp = {
                    employeeNumber: emp[0].outerText,
                    name: emp[1].outerText,
                    kana: emp[2].outerText,
                    mailAddress: emp[3].outerText,
                    password: emp[4].outerText
                }
            })
            self.update()

            $('#editModal').modal({
                // マルチディスプレイ使用時でもズレなくなる
                observeChanges: true
            }).modal('show');
        }

        prepareEdit()
        {
            // const edittedEmp = {}
            // $('editModalForm input text').each(function () {
            //     edittedEmp[this.name] = this.value
            // })
            //
        }

        delConfirm(e)
        {
            self.empNo = e.currentTarget.getAttribute('emp-no')
            self.empName = e.currentTarget.getAttribute('emp-name')
            self.update()
            $('#deleteModal').modal({
                // マルチディスプレイ使用時でもズレなくなる
                observeChanges: true
            }).modal('show');
        }

        del(e)
        {
            const employeeNumber = e.currentTarget.getAttribute('emp-no')
            $.ajax({
                type: 'GET',
                url: '/delete?employeeNumber=' + employeeNumber,
                contentType: 'application/json',
                dataType: 'json'
            }).done(function (data) {
                self.list()
                self.dispMessage('success', data.successes)
            }).fail(function (xhr) {
                self.dispMessage('fail', self.globalErrorHandler.handle(xhr))
            })
        }

        dispMessage(msgType, message)
        {
            if (msgType === 'success') {
                self.successes = message
                self.update()
                $('#successMsgBox').transition({
                    animation: 'fade in',
                    duration: '2s'
                });
            } else {
                self.errors = message
                self.update()
                $('#errorMsgBox').transition({
                    animation: 'fade in',
                    duration: '2s'
                });
            }
        }

        dispGlobalMessage()
        {
            const msg = self.globalMessage.take('createdEmployee')
            if (msg !== undefined) {
                self.dispMessage('success', msg)
            }
        }

        getEditEmp(propertyName)
        {
            const emp = self.editEmp
            if (emp === null || emp === undefined) {
                return ""
            }

            return emp[propertyName]
        }
    </script>
</list-employee>