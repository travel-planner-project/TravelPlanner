import styles from './AccountBook.module.scss'
import AccountItem from './AccountItem'

function AccountBook() {
  return (
    <div className={styles.container}>
      <AccountItem />
      <div className={styles.line} />

      <div>총 합계</div>
    </div>
  )
}

export default AccountBook
