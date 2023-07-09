import Icon from '../../../Common/Icon'
import styles from './AccountItem.module.scss'

function AccountItem() {
  return (
    <div className={styles.itemContainer}>
      <input placeholder='이용내역' className={styles.inputText} />
      <input placeholder='금액' type='number' step='1000' className={styles.cost} />
      <span className={styles.won}>원</span>
      <button type='button'>
        <Icon name='minus-square' size={10} />
      </button>
    </div>
  )
}

export default AccountItem
