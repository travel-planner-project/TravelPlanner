import styles from './Checklist.module.scss'
import ListItem from './ListItem'

function Checklist() {
  return (
    <div className={styles.container}>
      <ul className={styles.checklist}>
        <ListItem />
      </ul>
    </div>
  )
}

export default Checklist
