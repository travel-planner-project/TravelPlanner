import styles from './SearchBar.module.scss'

export default function SearchBar() {
  return (
    <form className={styles.form}>
      <input
        type='text'
        className={styles.input}
        placeholder='여행하고 싶은 지역을 입력하세요'
        required
      />
      <button type='submit' className={styles.searchBtn}>
        검색
      </button>
    </form>
  )
}
