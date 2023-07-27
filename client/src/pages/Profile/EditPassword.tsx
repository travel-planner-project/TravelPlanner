import { useState } from 'react'
import { useRecoilValue } from 'recoil'
import { userInfo } from '../../store/store'
import { CheckPassword } from '../../apis/user'
import RenderCheck from '../../components/Profile/RenderCheck'
import RenderEdit from '../../components/Profile/RenderEdit'

function EditPassword() {
  const { userId } = useRecoilValue(userInfo)
  const [isChecked, SetIsChecked] = useState(false)
  const [currentPassword, setCurrentPassword] = useState('')

  const onCheckedSubmit = (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault()
    CheckPassword({ userId: userId, password: currentPassword }).then(response => {
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

  return (
    <>
      {isChecked ? (
        <RenderEdit />
      ) : (
        <RenderCheck
          onCurrentPasswordChange={onCurrentPasswordChange}
          onCheckedSubmit={onCheckedSubmit}
        />
      )}
    </>
  )
}

export default EditPassword
