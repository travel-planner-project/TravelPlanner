import { Link } from 'react-router-dom'
import { useSetRecoilState } from 'recoil'
import { userInfo } from '../store/store'
import Password from '../components/SignIn/Password'
import Email from '../components/SignIn/Email'
import { signIn } from '../apis/user'
import useRouter from '../hooks/useRouter'
import styles from './SignIn.module.scss'

const UserInformation = {
  userId: 1,
  userNickname: '설화',
  email: 'test@test.com',
}

type responseDataType = {
  userInfo: {
    userId: number
    userNickname: string
    email: string
  }
}

function SignIn() {
  const { routeTo } = useRouter()
  const setUserInfo = useSetRecoilState(userInfo)
  const submitSignIn = async (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault()

    const formData = new FormData(event.currentTarget)
    const email = formData.get('email')
    const password = formData.get('password')
    const response = await signIn({ email, password })
    const { status, data }: { status: number; data: responseDataType } = response!

    if (status === 200) {
      setUserInfo(UserInformation) // 응답으로 받은 유저 정보 Recoil & 세션 스토리지 저장
      routeTo('/')
    } else if (status === 403) {
      alert('이메일 또는 비밀번호를 확인해 주세요')
    } else {
      alert('로그인에 실패했습니다. 잠시 후 다시 시도해주세요')
    }
  }

  return (
    <form className={styles.signInForm} onSubmit={submitSignIn}>
      <Email />
      <Password />
      <div className={styles.btnBox}>
        <button type='submit' className={styles.signInBtn}>
          로그인
        </button>
        <Link to='/user/register'>회원가입</Link>
        {/* <Link to='/findpassword'>비밀번호 찾기</Link> */}
      </div>
    </form>
  )
}

export default SignIn
