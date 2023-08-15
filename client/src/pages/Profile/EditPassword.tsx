import { useState } from 'react'
import useLogout from '../../hooks/useLogout'
import { useForm } from 'react-hook-form'
import { FormValueType } from '../../types/signUpTypes'
import { checkPassword, editPassword } from '../../apis/user'
import RenderCheck from '../../components/Profile/RenderCheck'
import RenderEdit from '../../components/Profile/RenderEdit'

function EditPassword() {
  const logout = useLogout()
  // 현재 패스워드 체크용
  const [isChecked, SetIsChecked] = useState(false)
  const [currentPassword, setCurrentPassword] = useState('')
  // 새 패스워드 수정용
  const {
    register,
    handleSubmit,
    formState: { dirtyFields, errors, isSubmitting },
    getValues,
  } = useForm<FormValueType>({ mode: 'onChange' })

  const onCheckSubmit = (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault()
    checkPassword({ password: currentPassword }).then(response => {
      if (response?.status === 200) {
        SetIsChecked(true)
      }
      if (response?.status !== 200) {
        alert('비밀번호가 일치하지 않습니다.')
      }
    })
  }

  const onCurrentPasswordChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setCurrentPassword(event.target.value)
  }

  const onEditSubmit = (data: FormValueType) => {
    editPassword({ password: data.password }).then(response => {
      if (response?.status === 200) {
        alert('비밀번호가 변경되었습니다. 다시 로그인해주세요.')
        logout()
      }
      if (response?.status !== 200) {
        alert('비밀번호 변경에 실패했습니다.')
      }
    })
  }

  const props = {
    register,
    onSubmit: handleSubmit(data => onEditSubmit(data)),
    dirtyFields,
    errors,
    getValues,
    isSubmitting,
  }

  return (
    <>
      {isChecked ? (
        <RenderEdit {...props} />
      ) : (
        <RenderCheck
          onCurrentPasswordChange={onCurrentPasswordChange}
          onCheckedSubmit={onCheckSubmit}
        />
      )}
    </>
  )
}

export default EditPassword
