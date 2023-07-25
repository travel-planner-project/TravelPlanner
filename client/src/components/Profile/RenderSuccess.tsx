import useRouter from '../../hooks/useRouter'
import styles from './RenderEdit.module.scss'

function RenderScuccess() {
  const { pathname, routeTo } = useRouter()

  return (
    <div className={styles.entireContainer}>
      <span>비밀번호가 변경되었습니다.</span>
      <button type='button' className={styles.confirmButton} onClick={() => routeTo('/')}>
        홈으로 가기
      </button>
    </div>
  )
}

export default RenderScuccess
