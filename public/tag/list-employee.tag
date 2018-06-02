<list-employee>
    <div class="ui container default-margin-top">
        <h1>
            社員一覧
        </h1>
        <div class="ui success message" id="successMsgBox" if={successes}>
            <i class="close icon"></i>
            <div class="header">
                完了
            </div>
            <p>{successes}</p>
        </div>
        <div class="ui error message" id="errorMsgBox" if={errors}>
            <i class="close icon"></i>
            <virtual each={values, key in errors}>
                <div class="header">{key}</div>
                <virtual each={v in values}>
                    <p>{v}</p>
                </virtual>
            </virtual>
        </div>
        <table class="ui very basic table" id="employeeTable">
            <tr>
                <th>社員番号</th>
                <th>氏名</th>
                <th>カナ</th>
                <th>メールアドレス</th>
                <th>最終更新日</th>
                <th>&nbsp;</th>
            </tr>
            <tr each={emp, i in employees} class="emp-row-{i}" onmouseover="style.background='#efffc4'" onmouseout="style.background='#ffffff'">
                <td>{ emp.employeeNumber }</td>
                <td>{ emp.name }</td>
                <td>{ emp.kana }</td>
                <td>{ emp.mailAddress }</td>
                <td>{ emp.updateDate }</td>
                <td class="right aligned">
                    <div class="tiny ui blue button" emp-index={i} onclick={jumpToAttendance}>勤怠</div>
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
            <div class="ui error message" id="editModalErrorMsgBox" if={modalErrors}>
                <i class="close icon"></i>
                <virtual each={values, key in modalErrors}>
                    <div class="header">{key}</div>
                    <virtual each={v in values}>
                        <p>{v}</p>
                    </virtual>
                </virtual>
            </div>
            <div class="ui form" id="editModalForm">
                <div class="two fields">
                    <div class="field">
                        <label>社員番号(変更不可)</label>
                        <input type="text" name="employeeNumber" value={getEditEmp('employeeNumber')} readonly="">
                    </div>
                    <div class="field">
                        <label>氏名<span class="required-input">*</span></label>
                        <input type="text" name="name" value={getEditEmp('name')}>
                    </div>
                </div>
                <div class="two fields">
                    <div class="field">
                        <label>カナ<span class="required-input">*</span></label>
                        <input type="text" name="kana" value={getEditEmp('kana')}>
                    </div>
                    <div class="field">
                        <label>メールアドレス<span class="required-input">*</span></label>
                        <input type="text" name="mailAddress" value={getEditEmp('mailAddress')}>
                    </div>
                </div>
                <div class="two fields">
                    <div class="field">
                        <label>新しいパスワード</label>
                        <input type="password" name="newPassword" value={getEditEmp('password')}>
                    </div>
                    <div class="field">
                        <label>新しいパスワード（確認）</label>
                        <input type="password" name="newPasswordConfirm" value={getEditEmp('password')}>
                    </div>
                </div>
                <input type="hidden" name="updateDate" value={getEditEmp('updateDate')}>
            </div>
        </div>
        <div class="actions">
            <div class="ui negative button">キャンセル</div>
            <div class="ui green right labeled icon button" onclick={edit}>
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
                self.errors = self.globalErrorHandler.handle(xhr)
                self.update()
                fadeMessage('errorMsgBox')
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
                    updateDate: emp[4].outerText
                }
            })
            self.modalErrors = null
            self.update()

            $('#editModal').modal({
                // マルチディスプレイ使用時でもズレなくなる
                observeChanges: true
            }).modal({
                onHidden: function () {
                    self.editEmp = null
                    self.update()
                }
            }).modal('show');
        }

        edit()
        {
            const editedEmp = {}
            $('#editModalForm').find('input:text, input:password, input:hidden').each(function () {
                editedEmp[this.name] = this.value
            })

            // 一覧ページに出ているメッセージを削除
            self.successes = null
            self.errors = null
            $.ajax({
                type: 'POST',
                url: '/edit',
                contentType: 'application/json',
                data: JSON.stringify(editedEmp)
            }).done(function (data) {
                self.successes = data.successes
                self.update()
                $('#editModal').modal('hide');
                fadeMessage('successMsgBox')
            }).fail(function (xhr) {
                self.modalErrors = self.globalErrorHandler.handle(xhr, function () {
                    $('#editModal').modal('hide');
                })
                self.update()
                fadeMessage('editModalErrorMsgBox')
            }).always(function () {
                self.list()
            })
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
                self.successes = data.successes
                self.update()
                fadeMessage('successMsgBox')
            }).fail(function (xhr) {
                self.errors = self.globalErrorHandler.handle(xhr)
                self.update()
                fadeMessage('errorMsgBox')
            })
        }

        dispGlobalMessage()
        {
            const msg = self.globalMessage.take('createdEmployee')
            if (msg !== undefined) {
                self.successes = msg
                self.update()
                fadeMessage('successMsgBox')
            }
        }

        jumpToAttendance()
        {
            route('/attendance')
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