import Icon from '../Commons/Icon'
import styles from './ShowPasswordButton.module.scss'

type ShowPasswordButtonProps = {
  show: boolean
  handleShow: () => void
}
function ShowPasswordButton({ show, handleShow }: ShowPasswordButtonProps) {
  return (
    <div className={styles.showPasswordBtn}>
      {show ? (
        <button type='button' onClick={handleShow}>
          <Icon name='eye' size={16} />
        </button>
      ) : (
        <button type='button' onClick={handleShow}>
          <Icon name='eye-close' size={16} />
        </button>
      )}
    </div>
  )
}

export default ShowPasswordButton
