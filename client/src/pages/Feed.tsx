import PlannerTitle from '../components/Feed/PlannerTitle'
import SearchBar from '../components/Feed/SearchBar'
import styles from './Feed.module.scss'

export default function Feed() {
  return (
    <div className={styles.container}>
      <h1 className={styles.headline}>다른 사람들의 여행 계획을 둘러보세요!</h1>
      <div className={styles.line} />
      <SearchBar />
      <PlannerTitle />
    </div>
  )
}
