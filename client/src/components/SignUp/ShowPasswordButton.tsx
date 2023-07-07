import Icon from '../Common/Icon'
import styles from './ShowPasswordButton.module.scss'

type ShowPasswordButtonProps = {
  show: { password: boolean; passwordCheck: boolean } | boolean
  handleShow: (type: 'password' | 'passwordCheck') => void
  labelName: 'password' | 'passwordCheck'
}
function ShowPasswordButton({ show, handleShow, labelName }: ShowPasswordButtonProps) {
  return (
    <div className={styles.showPasswordBtn}>
      {show ? (
        <button type='button' onClick={() => handleShow(labelName)}>
          <Icon name='eye' size={16} />
        </button>
      ) : (
        <button type='button' onClick={() => handleShow(labelName)}>
          <Icon name='eye-close' size={16} />
        </button>
      )}
    </div>
  )
}

export default ShowPasswordButton
